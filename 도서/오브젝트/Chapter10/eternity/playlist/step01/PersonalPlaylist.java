package 도서.오브젝트.Chapter10.eternity.playlist.step01;

public class PersonalPlaylist extends Playlist {
    public void remove(Song song) {
        getTracks().remove(song);
    }
}
