package main.java.util;

import java.util.HashSet;

public final class Utility {

	private HashSet<String> video;
	private HashSet<String> audio;
	private HashSet<String> image;

	private static Utility util;

	private Utility() {
		video = new HashSet<String>();
		video.add("mp4");

		audio = new HashSet<String>();
		audio.add("mp3");

		image = new HashSet<String>();
		image.add("png");
	}

	public static Utility getUtility() {
		if (util == null) {
			util = new Utility();
		}
		return util;
	}

	public boolean isVideo(String fileExtension) {
		return video.contains(fileExtension);
	}

	public boolean isAudio(String fileExtension) {
		return audio.contains(fileExtension);
	}

	public boolean isImage(String fileExtension) {
		return image.contains(fileExtension);
	}
}
