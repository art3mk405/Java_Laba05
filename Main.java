import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("           Laboratory Work #5");
        System.out.println("===========================================");
        System.out.println("Topic: Inheritance and polymorphism in Java");
        System.out.println("Variant: 4");
        System.out.println("===========================================\n");

        try (Scanner sc = new Scanner(System.in)) {
            Album album = createSampleAlbum();

            System.out.println("Initial album:");
            album.print();

            System.out.println("\nTotal duration of album: " +
                    album.getTotalDuration() + " seconds");

            album.sortByStyle();
            System.out.println("\nAlbum after sorting by style:");
            album.print();

            try {
                System.out.print("\nEnter minimum track length (seconds): ");
                int min = sc.nextInt();
                System.out.print("Enter maximum track length (seconds): ");
                int max = sc.nextInt();

                if (min > max) {
                    System.out.println("Error: min length is greater than max length.");
                } else {
                    List<Composition> found = album.findByLengthRange(min, max);
                    if (found.isEmpty()) {
                        System.out.println("No compositions found in this range.");
                    } else {
                        System.out.println("\nCompositions in this length range:");
                        for (Composition c : found) {
                            System.out.println(" - " + c);
                        }
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: please enter integer numbers for length.");
            }

            System.out.println("\nPolymorphic play() demo:");
            album.playAll();

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private static Album createSampleAlbum() {
        Album album = new Album("Student Demo Album");

        album.add(new RockComposition("Stone Road", 210));
        album.add(new PopComposition("Summer Night", 185));
        album.add(new JazzComposition("Blue Morning", 240));
        album.add(new RockComposition("Electric Storm", 195));
        album.add(new PopComposition("Simple Love", 160));
        album.add(new JazzComposition("Old Street", 300));

        return album;
    }
}

abstract class Composition {
    private String title;
    private int durationSec;
    private String style;

    public Composition(String title, int durationSec, String style) {
        this.title = title;
        this.durationSec = durationSec;
        this.style = style;
    }

    public String getTitle() {
        return title;
    }

    public int getDurationSec() {
        return durationSec;
    }

    public String getStyle() {
        return style;
    }

    public abstract void play();

    @Override
    public String toString() {
        return "\"" + title + "\" (" + style + ", " + durationSec + " sec)";
    }
}

class RockComposition extends Composition {
    public RockComposition(String title, int durationSec) {
        super(title, durationSec, "Rock");
    }

    @Override
    public void play() {
        System.out.println("Playing rock track: " + getTitle());
    }
}

class PopComposition extends Composition {
    public PopComposition(String title, int durationSec) {
        super(title, durationSec, "Pop");
    }

    @Override
    public void play() {
        System.out.println("Playing pop track: " + getTitle());
    }
}

class JazzComposition extends Composition {
    public JazzComposition(String title, int durationSec) {
        super(title, durationSec, "Jazz");
    }

    @Override
    public void play() {
        System.out.println("Playing jazz track: " + getTitle());
    }
}

class Album {
    private String name;
    private List<Composition> tracks = new ArrayList<>();

    public Album(String name) {
        this.name = name;
    }

    public void add(Composition c) {
        tracks.add(c);
    }

    public void print() {
        System.out.println("Album: " + name);
        for (int i = 0; i < tracks.size(); i++) {
            System.out.println((i + 1) + ". " + tracks.get(i));
        }
    }

    public int getTotalDuration() {
        int sum = 0;
        for (Composition c : tracks) {
            sum += c.getDurationSec();
        }
        return sum;
    }

    public void sortByStyle() {
        tracks.sort((a, b) -> a.getStyle().compareToIgnoreCase(b.getStyle()));
    }

    // пошук треків у діапазоні тривалості
    public List<Composition> findByLengthRange(int minSec, int maxSec) {
        List<Composition> result = new ArrayList<>();
        for (Composition c : tracks) {
            if (c.getDurationSec() >= minSec && c.getDurationSec() <= maxSec) {
                result.add(c);
            }
        }
        return result;
    }

    public void playAll() {
        for (Composition c : tracks) {
            c.play();
        }
    }
}
