package chapter6;

public class Exercise06 {

	private static final Concert CONCERT = new Concert("Concert 1", "Artist X", 100);
	private static final Movie MOVIE1 = new Movie("Movie 1", 2017, 120);
	private static final Movie MOVIE2 = new Movie("Movie 2", 2018, 130);

	public static void main(String[] args) {
		Show show = new CompositeShow(CONCERT, new CompositeShow(MOVIE1, MOVIE2));

		// 1) Unpack one level
		if (show instanceof CompositeShow composite) {
			for (Show sub : composite) {
				System.out.println(sub.description());
			}
		}

		System.out.println("---");

		// 2) Unpack recursively down to the leaves
		printLeaves(show);

		System.out.println("---");

		// 3) A decorated composite is NOT unpackable directly
		Show introduced = new IntroducedShow("Speaker 1", 10, show);
		System.out.println(introduced instanceof Iterable);
	}

	private static void printLeaves(Show pShow) {
		if (pShow instanceof CompositeShow composite) {
			for (Show sub : composite) {
				printLeaves(sub);
			}
		} else {
			System.out.println(pShow.description());
		}
	}
}