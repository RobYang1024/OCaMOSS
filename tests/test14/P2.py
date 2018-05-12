import csv

#Constants:
_TAG = 1

def main():
	training_data_file_path = "./train.txt"
	test_data_file_path = "./test.txt"
	development_data_file_path = "./development.txt"
	# label_matches = generate_input_with_unknown_words(training_data_file_path)
	# transition_unigram_counts, transition_bigram_counts, transition_bigram_counts_two_start_tokens, transition_trigram_counts = generate_transition_counts(training_data_file_path)
	# #transition_bigram_counts = generate_transition_bigram_counts(training_data_file_path)
	# #transition_trigram_counts = generate_transition_trigram_counts(training_data_file_path)
	# #transition_bigram_counts_two_start_tokens = generate_transition_bigram_counts_two_start_tokens(training_data_file_path)
	# smoothed_unigram_counts = kneser_ney_smoothing(transition_unigram_counts)
	# smoothed_bigram_counts = kneser_ney_smoothing(transition_bigram_counts)
	# smoothed_trigram_counts = kneser_ney_smoothing(transition_trigram_counts)
	# smoothed_bigram_counts_two_start_tokens = kneser_ney_smoothing(transition_bigram_counts_two_start_tokens)
	# bigram_probabilities = generate_transition_bigram_probabilities(smoothed_unigram_counts, smoothed_bigram_counts)
	# trigram_probabilities = generate_transition_trigram_probabilities(smoothed_bigram_counts_two_start_tokens, smoothed_trigram_counts)
	# #bigram_label_index_dict = process_test_data_bigrams(test_data_file_path, label_matches, bigram_probabilities)
	# trigram_label_index_dict = process_test_data_trigrams(test_data_file_path, label_matches, trigram_probabilities)
	# # for tag_trigram in trigram_label_index_dict:
	# # 	print(str(tag_trigram)+ ": " + str(trigram_label_index_dict[tag_trigram]))
	# # export_label_index_dict(trigram_label_index_dict)
	calculate_results(development_data_file_path)

def generate_input(file_path):
	"""Reads the file with the given file_path.
	Creates dictionary of all words encountered in following format:
	{(word, tag) : count}"""
	label_matches = dict()
	file_lines = []
	with open(file_path) as f:
		for line in f:
			file_lines = file_lines + [line.lower().split()]
		word_tuples = zip(file_lines[0::3], file_lines[1::3], file_lines[2::3])
		for (words, part_of_speech, word_type) in word_tuples:
			type_tuples = zip(words, word_type)
			for word_and_tag in type_tuples:
				label_matches.update({word_and_tag : (label_matches.get(word_and_tag, 0) + 1)})
	return label_matches

def generate_input_with_unknown_words(file_path):
	"""Reads the file with the given file_path.
	Replaces every first occurance of a (word, tag) pair with ("<UNK>", tag).
	Creates dictionary of all words encountered in following format:
	{(word, tag) : count}"""
	seen_tuples = []
	label_matches = dict()
	file_lines = []
	with open(file_path) as f:
		for line in f:
			file_lines = file_lines + [line.lower().split()]
		word_tuples = zip(file_lines[0::3], file_lines[1::3], file_lines[2::3])
		for (words, part_of_speech, word_type) in word_tuples:
			type_tuple = zip(words, word_type)
			for word_and_tag in type_tuple:
				if word_and_tag in seen_tuples:
					label_matches.update({word_and_tag : (label_matches.get(word_and_tag, 0) + 1)})
				else:
					tag = word_and_tag[1]
					unknown_entry = ("<UNK>", tag)
					label_matches.update({unknown_entry : (label_matches.get(unknown_entry, 0) + 1)})
					seen_tuples.append(word_and_tag)
	return label_matches

def get_tag_options(label_matches):
	"""Returns a list of the unique tags present in label_matches."""
	tag_options = []
	for key in label_matches.keys():
		if key[1] not in tag_options:
			tag_options.append(key[1])
	return tag_options

def get_known_words(label_matches):
	"""Returns a list of the unique words present in label_matches."""
	known_words = set()
	for key in label_matches.keys():
		if key[0] not in known_words:
			known_words.add(key[0])
	return known_words

def generate_transition_counts(file_path):
	"""Reads the file with the given file_path.
	Creates dictionary containing the transition unigram counts 
	in the following format:
	{tag : count}
	"""
	transition_unigram_counts = dict()
	transition_bigram_counts = dict()
	transition_bigram_counts_two_start_tokens = dict()
	transition_trigram_counts = dict()

	with open(file_path) as f:
		line_count = 0
		for line in f:
			line_count+=1
			if line_count%3 != 0:
				continue
			tag_set = ["<START>"] + line.lower().split()
			i = 0
			while(i<len(tag_set)):
				tag = tag_set[i]
				if(tag in transition_unigram_counts):
					transition_unigram_counts[tag]+=1
				else:
					transition_unigram_counts[tag]=1
				if(i>0):
					tag_bigram = (tag_set[i-1], tag_set[i])
					if tag_bigram not in transition_bigram_counts:
						transition_bigram_counts[tag_bigram] = 1
					else:
						transition_bigram_counts[tag_bigram]+=1
				i+=1
			tag_set_2 = ["<START>"] + tag_set
			i = 1
			while(i<len(tag_set_2)):
				tag_bigram = (tag_set_2[i-1], tag_set_2[i])
				if tag_bigram not in transition_bigram_counts_two_start_tokens:
					transition_bigram_counts_two_start_tokens[tag_bigram] = 1
				else:
					transition_bigram_counts_two_start_tokens[tag_bigram]+=1
				
				if(i>1):
					tag_trigram = (tag_set_2[i-2], tag_set_2[i-1], tag_set_2[i])
					if tag_trigram not in transition_trigram_counts:
						transition_trigram_counts[tag_trigram] = 1
					else:
						transition_trigram_counts[tag_trigram]+=1

				i+=1
	return (transition_unigram_counts, transition_bigram_counts, transition_bigram_counts_two_start_tokens, transition_trigram_counts)

def generate_transition_bigram_counts(file_path):
	"""Reads the file with the given file_path.
	Creates dictionary containing the transition bigram counts
	in the following format:
	{(tag[i-1], tag[i]) : count}
	"""
	transition_bigram_counts = dict()
	with open(file_path) as f:
		line_count = 0
		for line in f:
			line_count+=1
			if line_count%3 != 0:
				continue
			tag_set = ["<START>"] + line.lower().split()
			i = 1
			while(i<len(tag_set)):
				tag_tuple = (tag_set[i-1], tag_set[i])
				if tag_tuple not in transition_bigram_counts:
					transition_bigram_counts[tag_tuple] = 1
				else:
					transition_bigram_counts[tag_tuple]+=1
				i+=1
	return transition_bigram_counts

def generate_transition_bigram_counts_two_start_tokens(file_path):
	"""Reads the file with the given file_path.
	Creates dictionary containing the transition bigram counts
	in the following format:
	{(tag[i-1], tag[i]) : count}
	"""
	transition_bigram_counts = dict()
	with open(file_path) as f:
		line_count = 0
		for line in f:
			line_count+=1
			if line_count%3 != 0:
				continue
			tag_set = ["<START>"] + ["<START>"] + line.lower().split()
			i = 1
			while(i<len(tag_set)):
				tag_tuple = (tag_set[i-1], tag_set[i])
				if tag_tuple not in transition_bigram_counts:
					transition_bigram_counts[tag_tuple] = 1
				else:
					transition_bigram_counts[tag_tuple]+=1
				i+=1
	return transition_bigram_counts

def generate_transition_trigram_counts(file_path):
	"""Reads the file with the given file_path.
	Creates dictionary containing the transition trigram counts 
	in the following format:
	{(tag[i-2], tag[i-1], tag[i]) : count}
	"""
	transition_trigram_counts = dict()
	with open(file_path) as f:
		line_count = 0
		for line in f:
			line_count+=1
			if line_count%3 != 0:
				continue
			tag_set = ["<START>"] + ["<START>"] + line.lower().split()
			i = 2
			while(i<len(tag_set)):
				tag_tuple = (tag_set[i-2], tag_set[i-1], tag_set[i])
				if tag_tuple not in transition_trigram_counts:
					transition_trigram_counts[tag_tuple] = 1
				else:
					transition_trigram_counts[tag_tuple]+=1
				i+=1
	return transition_trigram_counts

def generate_transition_bigram_probabilities(transition_unigram_counts, transition_bigram_counts):
	"""Takes in the unigram and bigram count matrices.
	Creates dictionary containing the transition bigram 
	probabilities in the following format:
	{(tag[i-1], tag[i]) : probability}
	where the probability is calculated by the following formula:
	probability((tag[i-1], tag[i])) = count((tag[i-1], tag[i]))/count(tag[i-1])
	"""
	transition_bigram_probabilities = dict()
	for tag_bigram in transition_bigram_counts:
		transition_bigram_probabilities[tag_bigram] = float(transition_bigram_counts[tag_bigram])/transition_unigram_counts[tag_bigram[0]]
	return transition_bigram_probabilities

def generate_transition_trigram_probabilities(transition_bigram_counts, transition_trigram_counts):
	"""Takes in the bigram and trigram count matrices.
	Creates dictionary containing the transition trigram 
	probabilities in the following format:
	{(tag[i-2], tag[i-1], tag[i]) : probability}
	where the probability is calculated by the following formula:
	probability((tag[i-2], tag[i-1], tag[i])) = count((tag[-2], tag[i-1], tag[i]))/count((tag[i-2], tag[i-1]))
	"""
	transition_trigram_probabilities = dict()
	for tag_trigram in transition_trigram_counts:
		transition_trigram_probabilities[tag_trigram] = float(transition_trigram_counts[tag_trigram])/transition_bigram_counts[(tag_trigram[0], tag_trigram[1])]
	return transition_trigram_probabilities

def get_tag_counts(label_matches):
	"""Returns a dictionary with the counts of all the tags in label_matches"""
	tag_counts = {}
	for word_and_tag in label_matches.keys():
		current_count = tag_counts.get(word_and_tag[_TAG], 0)
		tag_counts[word_and_tag[_TAG]] = current_count+1
	return tag_counts

def get_emissions_probability(label_matches, given_tag, given_word, tag_counts):
	"""Calculates the emissions probability of associating a given
	tag and word by the following formula:
	emissions_probability = (count of word associated with tag) / (count of tag)
	"""
	lookup_tuple = (given_word, given_tag)
	word_tag_frequency = label_matches.get(lookup_tuple, 0)
	tag_frequency = tag_counts[given_tag]
	if tag_frequency == 0:
		emissions_probability = 0
	else:
		emissions_probability = float(word_tag_frequency)/float(tag_frequency)
	return emissions_probability

def kneser_ney_smoothing(transition_probabilities):
	"""Smoothes the given label_matches dictionary using the Kneser-Ney method.
	-0.75 for frequency>=2 times.
	-0.5 for once.
	"""
	for tag_tuple in transition_probabilities.keys():
		count = transition_probabilities[tag_tuple]
		if count <2:
			transition_probabilities[tag_tuple] = count - 0.5
		else:
			transition_probabilities[tag_tuple] = count - 0.75
	return transition_probabilities

def viterbi_bigrams(transition_probabilities, label_matches, prev_tag, word, tag_possibilities):
	"""Implements the viterbi algorithm based on bigram probabilities.
	Cycles through the possible tags, calculates the emissions probability of the tag
	with the given word and the transition probability between the tag and the previous
	tag. The ultimate probability is the product of the two. Returns the tag with the highest
	probability."""
	max_prob = 0
	best_tag = ""
	tag_counts = get_tag_counts(label_matches)
	for tag in tag_possibilities:
		emissions_probability = get_emissions_probability(label_matches, tag, word, tag_counts)
		tag_bigram = (prev_tag,tag)
		transition_probability = transition_probabilities.get(tag_bigram, 0.000027)
		prob = emissions_probability * transition_probability
		if prob > max_prob:
			max_prob = prob
			best_tag = tag
	if best_tag == "":
		best_tag = "o"
	return best_tag

def viterbi_trigrams(transition_probabilities, label_matches, prev_tag, prev_prev_tag, word, tag_possibilities):
	"""Implements the viterbi algorithm based on trigram probabilities.
	Cycles through the possible tags, calculated the emiisions probability of the tag
	with the given word and the transition probability between the tag and the previous
	tag. The ultimate probability is the product of the two. Returns the tag with the highest
	probability."""
	max_prob = 0
	best_tag = ""
	tag_counts = get_tag_counts(label_matches)
	for tag in tag_possibilities:
		emissions_probability = get_emissions_probability(label_matches, tag, word, tag_counts)
		tag_trigram = (prev_prev_tag, prev_tag,tag)
		transition_probability = transition_probabilities.get(tag_trigram, 0.000027)
		prob = emissions_probability * transition_probability
		if prob > max_prob:
			max_prob = prob
			best_tag = tag
	if best_tag == "":
		best_tag = "o"
	return best_tag

def process_test_data_bigrams(file_path, label_matches, transition_probabilities):
	"""Parses the text data from the input file_path and processes it sentence by 
	sentence, using a model built on bigrams"""
	label_index_dict = {}
	starting_index = 0
	line_number = 0
	with open(file_path) as f:
		for line in f:
			if line_number%3 == 0:
				line = line.strip().lower().split('\t')
				label_index_dict = process_sentence_bigrams(line, starting_index, label_matches, label_index_dict, transition_probabilities)
				starting_index += len(line)
			line_number +=1
	return label_index_dict

def process_sentence_bigrams(sentence, base_index, label_matches, label_index_dict, transition_probabilities):
	"""Goes through a sentence word by word, using the viterbi 
	algorithm (with bigrams) to determine the correct tag. 
	If a word is not in the test set it is marked <UNK>.
	Returns a dictionary that has labels (besides 'o') and 
	the corresponding indices of the words that belong to them."""
	"New line!"
	sentence_process_index = 0
	label = ''
	prev_tag = "<START>"
	known_words = get_known_words(label_matches)
	tag_options = get_tag_options(label_matches)
	prev_is_valid = False
	curr_is_valid = False
	starting_index = 0
	ending_index = starting_index
	while sentence_process_index < len(sentence):
		word = sentence[sentence_process_index]
		if word in known_words:
			lookup_word = word
		else:
			lookup_word = "<UNK>"
		best_tag = viterbi_bigrams(transition_probabilities, label_matches, prev_tag, lookup_word, tag_options)
		curr_is_valid = (best_tag != 'o') and (len(best_tag)>2)
		if prev_is_valid and curr_is_valid and (best_tag[2:] != prev_tag[2:]):
			label_index_dict = add_to_label_index_dict(prev_tag[2:], base_index+starting_index, base_index+ending_index, label_index_dict)
			starting_index = sentence_process_index
			ending_index = starting_index
		elif curr_is_valid and prev_is_valid and (best_tag[2:] == prev_tag[2:]):
			ending_index +=1
		elif curr_is_valid:
			starting_index = sentence_process_index
			ending_index = starting_index
		elif prev_is_valid:
			label_index_dict = add_to_label_index_dict(prev_tag[2:], base_index+starting_index, base_index+ending_index, label_index_dict)
			starting_index = sentence_process_index
			ending_index = starting_index
		prev_tag = best_tag
		prev_is_valid = curr_is_valid
		sentence_process_index +=1
	return label_index_dict

def process_test_data_trigrams(file_path, label_matches, transition_probabilities):
	"""Parses the text data from the input file_path and processes it 
	sentence by sentence, using a model built on trigrams"""
	label_index_dict = {}
	starting_index = 0
	line_number = 0
	with open(file_path) as f:
		for line in f:
			if line_number%3 == 0:
				line = line.strip().lower().split('\t')
				label_index_dict = process_sentence_trigrams(line, starting_index, label_matches, label_index_dict, transition_probabilities)
				starting_index += len(line)
			line_number +=1
	return label_index_dict

def process_sentence_trigrams(sentence, base_index, label_matches, label_index_dict, transition_probabilities):
	"""Goes through a sentence word by word, using the viterbi 
	algorithm (with trigrams) to determine the correct tag. 
	If a word is not in the test set it is marked <UNK>.
	Returns a dictionary that has labels (besides 'o') and 
	the corresponding indices of the words that belong to them."""
	sentence_process_index = 0
	label = ''
	prev_tag = "<START>"
	prev_prev_tag = "<START>"
	known_words = get_known_words(label_matches)
	tag_options = get_tag_options(label_matches)
	prev_is_valid = False
	curr_is_valid = False
	starting_index = 0
	ending_index = starting_index
	while sentence_process_index < len(sentence):
		word = sentence[sentence_process_index]
		if word in known_words:
			lookup_word = word
		else:
			lookup_word = "<UNK>"
		best_tag = viterbi_trigrams(transition_probabilities, label_matches, prev_tag, prev_prev_tag, lookup_word, tag_options)
		curr_is_valid = (best_tag != 'o') and (len(best_tag)>2)
		if prev_is_valid and curr_is_valid and (best_tag[2:] != prev_tag[2:]):
			label_index_dict = add_to_label_index_dict(prev_tag[2:], base_index+starting_index, base_index+ending_index, label_index_dict)
			starting_index = sentence_process_index
			ending_index = starting_index
		elif curr_is_valid and prev_is_valid and (best_tag[2:] == prev_tag[2:]):
			ending_index +=1
		elif curr_is_valid:
			starting_index = sentence_process_index
			ending_index = starting_index
		elif prev_is_valid:
			label_index_dict = add_to_label_index_dict(prev_tag[2:], base_index+starting_index, base_index+ending_index, label_index_dict)
			starting_index = sentence_process_index
			ending_index = starting_index
		prev_prev_tag = prev_tag
		prev_tag = best_tag
		prev_is_valid = curr_is_valid
		sentence_process_index +=1
	return label_index_dict

def add_to_label_index_dict(label, starting_index, ending_index, label_index_dict):
	"""Adds the given indices to the label_index_dict,
	where the label is the key."""
	label = label.upper()
	if label in label_index_dict.keys():
		label_index_dict[label] = label_index_dict[label] + ' ' + str(starting_index) + '-' + str(ending_index)
	else:
		label_index_dict[label] = str(starting_index) + '-' + str(ending_index)
	return label_index_dict

def export_label_index_dict(label_index_dict):
	""" Exports the dictionary that contains labels and corresponding indices
	of words to a csv file."""
	csv_file = open('output.csv', 'w')
	writer = csv.writer(csv_file)
	row = ''
	header = 'Type,Prediction\n'
	csv_file.write(header)
	for key in label_index_dict.keys():
		row = key + ',' + label_index_dict[key] + '\n'
		csv_file.write(row)

def calculate_results(file_path):
	"""Calculates precision, recall and the F1 score on the development set
	"""
	org_dict = dict()
	misc_dict = dict()
	per_dict = dict()
	loc_dict = dict()
	with open("development_output.csv") as f:
		count = 0;
		for line in f:
			count+=1
			if count is 2:
				l = line[4:] #string without Category
				pairs = l.split()
				org_dict = {tuple(pair.split('-')) : 'ORG' for pair in pairs}
			if count is 3:
				l = line[5:] #string without Category
				pairs = l.split()
				misc_dict = {tuple(pair.split('-')) : 'MISC' for pair in pairs}
			if count is 4:
				l = line[4:] #string without Category
				pairs = l.split()
				per_dict = {tuple(pair.split('-')) : 'PER' for pair in pairs}
			if count is 5:
				l = line[4:] #string without Category
				pairs = l.split()
				loc_dict = {tuple(pair.split('-')) : 'LOC' for pair in pairs}
	
	with open(file_path) as f:
		count = 0
		total_entities = 0
		gold_entities = 0
		true_positives = 0
		predicted_entities = len(org_dict)+len(misc_dict)+len(per_dict)+len(loc_dict) 
		for line in f:
			count+=1
			if(count%3!=0):
				continue
			tag_set = line.lower().split()
			i =0
			is_in_middle_of_word = False
			starting_index = 0
			ending_index = 0
			tag_to_match = ""
			while(i<len(tag_set)):
				tag = tag_set[i]

				#print(tag)
				total_entities+=1

				if(is_in_middle_of_word):
					if(tag.split("-")[0] is "i"):
						ending_index+=1
					else:
						if(tag_to_match == "b-org"):
							if((str(starting_index), str(ending_index)) in org_dict):
								true_positives+=1
						elif(tag_to_match == "b-misc"):
							if((str(starting_index), str(ending_index)) in misc_dict):
								true_positives+=1
						elif(tag_to_match == "b-per"):
							if((str(starting_index), str(ending_index)) in per_dict):
								true_positives+=1
						elif(tag_to_match == "b-loc"):
							if((str(starting_index), str(ending_index)) in loc_dict):
								true_positives+=1
						is_in_middle_of_word = False
				if("b" in tag):
					gold_entities+=1
					is_in_middle_of_word = True
					starting_index = ending_index = total_entities-1
					tag_to_match = tag
					#starting_index=predicted_entities
					# while(tag!="o" and i<len(tag_set)):
					# 	i+=1
					# 	predicted_entities+=1
					# ending_index=predicted_entities-1
					# if(tag is "b-org"):
					# 	if((starting_index, ending_index) in org_dict):
					# 		true_positives+=1

				i+=1	



	print("Total entities: "+str(total_entities))
	print("Gold entities: "+str(gold_entities))
	print("True positives: " + str(true_positives))
	precision = float(true_positives)/predicted_entities
	recall = float(true_positives)/gold_entities
	print("Precision: " + str(precision))
	print("Recall: " + str(recall))
	print("F1 score: " + str((2*precision*recall)/(precision+recall)))

if __name__ == '__main__':
	main()