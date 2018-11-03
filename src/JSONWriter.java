
public class JSONWriter {

	public static String makeJSONString (int[][] gridState) {
		String writeJSON = "[";
		for(int[] i: gridState) {
			writeJSON += ("[");
			for(int j: i) {
				writeJSON += ("\"" + j + "\"" + ",");
			}
			writeJSON = writeJSON.substring(0, writeJSON.length()-1);
			writeJSON += "],";
		}
		writeJSON = writeJSON.substring(0, writeJSON.length()-1);
		writeJSON += "]";
		return writeJSON;
		/*int x = 0;
		int y = 0;
		String writeJSON = "{";
		for(int[] i: gridState) {
			writeJSON += ("\"" + x + "\": { ");
			for(int j: i) {
				writeJSON += ("\"" + y + "\":" + j + ",");
				y += 1;
			}
			y = 0;
			x += 1;
			writeJSON = writeJSON.substring(0, writeJSON.length()-1);
			writeJSON += "},";
		}
		writeJSON = writeJSON.substring(0, writeJSON.length()-1);
		writeJSON += "}";
		return writeJSON;*/
	}
}
