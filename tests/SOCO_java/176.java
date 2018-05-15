
public class ImageFile
{
	private String imageUrl;
	private int imageSize;

	public ImageFile(String url, int size)
	{
		imageUrl=url;
		imageSize=size;
	}

	public String getImageUrl()
	{
		return imageUrl;
	}

	public int getImageSize()
	{
		return imageSize;
	}
}
