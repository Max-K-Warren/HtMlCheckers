
public class JSONTest {

	public static void main(String[] args) {
		int[][] test= {{0,5,2,5,4,5,2,5},{5,2,5,4,5,1,5,3},{3,5,1,5,2,5,2,5},{5,4,5,3,5,2,5,3},
		{0,5,1,5,3,5,1,5},{5,2,5,2,5,1,5,3},{0,5,0,5,4,5,4,5},{5,2,5,4,5,3,5,3}};
		System.out.println(JSONWriter.makeJSONString(test));
	}
}
