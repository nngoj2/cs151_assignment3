package photoAlbum;




public class PhotoAlbumApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("program start");

	     PhotoAlbumView viewer = new PhotoAlbumView();
	     PhotoAlbumModel model = new PhotoAlbumModel();
	     model.addViewer(viewer);
	     model.addPhoto(new Photo(""));


		
		
		
		
	}
}
