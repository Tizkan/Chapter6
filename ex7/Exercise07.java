package chapter6.ex7;

import java.util.Iterator;

public class Exercise07 {

	private static final Concert CONCERT = new Concert("Concert 1", "Artist X", 100);
	private static final Movie MOVIE1 = new Movie("Movie 1", 2017, 120);
	private static final Movie MOVIE2 = new Movie("Movie 2", 2018, 130);

	public static void main(String[] args) {
		Show show = new CompositeShow(CONCERT, new CompositeShow(MOVIE1, MOVIE2));

		// 1) Unpack one level, no instanceof needed
		for (Show sub : show) {
			System.out.println(sub.description());
		}

		System.out.println("---");

		// 2) Unpack recursively down to the leaves
		printLeaves(show);

		System.out.println("---");

		// 3) A decorated composite can now be unpacked too
		Show introduced = new IntroducedShow("Speaker 1", 10, show);
		for (Show sub : introduced) {
			System.out.println(sub.description());
		}

		System.out.println("---");

		// 4) A leaf has nothing to unpack
		System.out.println(MOVIE1.iterator().hasNext());
	}

	private static void printLeaves(Show pShow) {
		Iterator<Show> iterator = pShow.iterator();
		if (!iterator.hasNext()) {
			System.out.println(pShow.description());
		} else {
			while (iterator.hasNext()) {
				printLeaves(iterator.next());
			}
		}
	}
}