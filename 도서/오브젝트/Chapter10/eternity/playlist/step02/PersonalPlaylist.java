package 도서.오브젝트.Chapter10.eternity.playlist.step02;

public class PersonalPlaylist extends Playlist {
    public void remove(Song song) {
        getTracks().remove(song);
        getSingers().remove(song.getSinger());
    }
}

