#include <stdlib.h>
#include <stdio.h>
#include "heaplib.h"
#include <string.h>
#include <stdbool.h>
#include <stdint.h>
#include <assert.h>
#include <limits.h>

/* Useful shorthand: casts a pointer to a (char *) before adding */
#define ADD_BYTES(base_addr, num_bytes) (((char *)(base_addr)) + (num_bytes))

typedef struct _block_info_t {
		unsigned int block_size;
		//unsigned int alloc_status;
	}block_info_t;

void coalesceNext(block_info_t* block) {
	block_info_t * next_ptr = ((block_info_t *)ADD_BYTES(block, block->block_size));
	if((next_ptr->block_size%2)==0) {
		block->block_size += next_ptr->block_size;
	}
}

/* See the .h for the advertised behavior of this library function.
 * These comments describe the implementation, not the interface.
 *
 * YOUR COMMENTS GO HERE.
 */
void hl_init(void *heap, unsigned int heap_size) {

	int start_offset = ((uintptr_t)(heap) % 8==0)?0:(8 - ((uintptr_t)(heap) % 8));

	int * heap_size_val = (int *)(ADD_BYTES(heap, start_offset));
	*heap_size_val = (heap_size - start_offset - 8);

	block_info_t * start_ptr = (block_info_t *)(ADD_BYTES(heap, start_offset+4));
	start_ptr->block_size = heap_size - start_offset - 8;
    return;
}

/* See the .h for the advertised behavior of this library function.
 * These comments describe the implementation, not the interface.
 *
 * YOUR COMMENTS GO HERE.
*/
void *hl_alloc(void *heap, unsigned int block_size) {

	int start_offset = ((uintptr_t)(heap) % 8==0)?0:(8 - ((uintptr_t)(heap) % 8));
	int heap_size = *((int *)(ADD_BYTES(heap, start_offset)));

	int bestSize = INT_MAX;
	block_info_t * bestBlock = NULL;
	
	block_info_t * current_ptr = (block_info_t *)(ADD_BYTES(heap, start_offset+4));

	if(block_size==0){
		return (void *)(current_ptr);
	}

	int totalSize =  0;

	while(totalSize<(heap_size)) {
		totalSize+=((current_ptr->block_size/2)*2);


		if((current_ptr->block_size >= (block_size + (8 - ((4+block_size)%8)) + 4))&&((current_ptr->block_size % 2) == 0)) {
			if(current_ptr->block_size < bestSize) {
				bestBlock = current_ptr;
				bestSize = current_ptr->block_size;
			}
		}
		if(totalSize<heap_size) {
			current_ptr = (block_info_t *)(ADD_BYTES(current_ptr, ((current_ptr->block_size/2)*2)));
		}
	}

	if(!bestBlock) {
		return 0;
	}

	bestBlock->block_size+=1;
	
	if(bestBlock->block_size >= (4 + block_size + (8 - ((4 + block_size)%8)) + 8)) {

		block_info_t * splitBlock = (block_info_t *)(ADD_BYTES(bestBlock, 4 + block_size + (8 - (((4 +block_size)%8)))));
		splitBlock->block_size = ((bestSize/2)*2) - (block_size + (8 - (4+block_size)%8) + 4);

		bestBlock->block_size = block_size + (8 - (4+block_size)%8) + 5;

		if(current_ptr!=bestBlock) {
			coalesceNext(splitBlock);
		}

	}
	
    return ((void *)ADD_BYTES(bestBlock, 4));
}

/* See the .h for the advertised behavior of this library function.
 * These comments describe the implementation, not the interface.
 *
 * YOUR COMMENTS GO HERE.
 */
void hl_release(void *heap, void *block) {

	if(block){
		block_info_t * block_ptr = (block_info_t *)(ADD_BYTES(block, -4));
		block_ptr->block_size = ((block_ptr->block_size/2)*2);

		int start_offset = ((uintptr_t)(heap) % 8==0)?0:(8 - ((uintptr_t)(heap) % 8));
		int heap_size = *((int *)(ADD_BYTES(heap, start_offset)));

		int totalSize =  0;
		block_info_t * prev_ptr = NULL;
		block_info_t * current_ptr = (block_info_t *)(ADD_BYTES(heap, start_offset+4));
		block_info_t * next_ptr = NULL;

		while(totalSize<(heap_size)) {
			totalSize+=((current_ptr->block_size/2)*2);
			if(((block_info_t *)(ADD_BYTES(current_ptr, 4)))==block) {
				break;
			}
			if (totalSize<(heap_size)) {
				prev_ptr = current_ptr;
				current_ptr = ((block_info_t *)(ADD_BYTES(current_ptr, ((current_ptr->block_size/2)*2))));
			}	
		}
		
		if(totalSize<heap_size) {
			next_ptr = ((block_info_t *)(ADD_BYTES(current_ptr, ((current_ptr->block_size/2)*2))));
		}
		
		if(next_ptr) {
			coalesceNext(current_ptr);
		}

		if(prev_ptr) {

			if((prev_ptr->block_size%2) == 0) {
				coalesceNext(prev_ptr);
			}

		}
		
	}

	return;

}

/* See the .h for the advertised behavior of this library function.
 * These comments describe the implementation, not the interface.
 *
 * YOUR COMMENTS GO HERE.
 */
void *hl_resize(void *heap, void *block, unsigned int new_size) {

	if(!block) {
		return hl_alloc(heap, new_size);
	}
	block_info_t * block_ptr = (block_info_t *)(ADD_BYTES(block, -4));
	int start_offset = ((uintptr_t)(heap) % 8==0)?0:(8 - ((uintptr_t)(heap) % 8));
	int heap_size = *((int *)(ADD_BYTES(heap, start_offset)));

	int bestSize = INT_MAX;
	block_info_t * bestBlock = NULL;
	
	block_info_t * current_ptr = (block_info_t *)(ADD_BYTES(heap, start_offset+4));
	//block_info_t * prev_ptr = NULL;

	//block_info_t * prev_block_ptr = NULL;

	int totalSize =  0;

	while(totalSize<(heap_size)) {
		totalSize+=((current_ptr->block_size/2)*2);

		if((current_ptr->block_size >= (new_size + (8 - ((4+new_size)%8)) + 4))&&(((current_ptr->block_size%2) == 0)
			||(current_ptr == block_ptr))) {
			if(current_ptr->block_size < bestSize) {
				bestBlock = current_ptr;
				bestSize = current_ptr->block_size;
			}
		}

		if(current_ptr == block_ptr) {
			//prev_block_ptr = prev_ptr;
		}

		if(totalSize<heap_size) {
			//prev_ptr = current_ptr;
			current_ptr = (block_info_t *)(ADD_BYTES(current_ptr, ((current_ptr->block_size/2)*2)));
		}
	}
	//printf("After the while loop\n");
	if(!bestBlock) {
		return 0;
	}


	block_ptr->block_size = (block_ptr->block_size/2)*2;

	int content_copy_size = (((block_ptr->block_size-4) > new_size)?new_size:(block_ptr->block_size-4));
	memmove(((void *)(ADD_BYTES(bestBlock, 4))), block, content_copy_size);

	//printf("After memmove\n");

	bestBlock->block_size = ((bestBlock->block_size/2)*2)+1;
	
	if(bestBlock->block_size >= (4 + new_size + (8 - ((4+new_size)%8)) + 8)) {

		block_info_t * splitBlock = (block_info_t *)(ADD_BYTES(bestBlock, 4 + new_size + (8 - (((4 +new_size)%8)))));
		splitBlock->block_size = ((bestSize/2)*2) - (new_size + (8 - (4+new_size)%8) + 4);
		//printf("%d\n", bestSize);
		//printf("%d\n", splitBlock->block_size);

		bestBlock->block_size = new_size + (8 - (4+new_size)%8) + 5;
		//printf("%d\n", bestBlock->block_size);

		if(current_ptr!=bestBlock) {
			coalesceNext(splitBlock);
		}
	}
	/*
	if(bestBlock!= block_ptr) {
		if(block_ptr!=current_ptr) {
			coalesceNext(block_ptr);
		}

		if(prev_block_ptr) {
			if(prev_block_ptr->block_size%2==0) {
				coalesceNext(prev_block_ptr);
			}
		}
	}
	*/
	
    return ((void *)ADD_BYTES(bestBlock, 4));
}
