package com.ali.rnp.khodemon.ExpandableTags;

import com.ali.rnp.khodemon.R;
import java.util.Arrays;
import java.util.List;

public class TagsDataFactory {

    public static List<SingleCheckGroup> makeSingleCheckTags() {
        return Arrays.asList( makeSingleCheckDr(),
                makeSingleCheckRockGenre(),
                makeSingleCheckJazzGenre(),
                makeSingleCheckClassicGenre(),
                makeSingleCheckSalsaGenre(),
                makeSingleCheckBluegrassGenre() );
    }


    public static SingleCheckGroup makeSingleCheckRockGenre() {
        return new SingleCheckGroup("Rock", makeRockArtists(), R.drawable.ic_electric_guitar);
    }

    public static List<Expert> makeRockArtists() {
        Expert queen = new Expert("Queen", true);
        Expert styx = new Expert("Styx", false);
        Expert reoSpeedwagon = new Expert("REO Speedwagon", false);
        Expert boston = new Expert("Boston", true);

        return Arrays.asList(queen, styx, reoSpeedwagon, boston);
    }

    public static SingleCheckGroup makeSingleCheckJazzGenre() {
        return new SingleCheckGroup("Jazz", makeJazzArtists(), R.drawable.ic_saxaphone);
    }

    public static List<Expert> makeJazzArtists() {
        Expert milesDavis = new Expert("Miles Davis", true);
        Expert ellaFitzgerald = new Expert("Ella Fitzgerald", true);
        Expert billieHoliday = new Expert("Billie Holiday", false);

        return Arrays.asList(milesDavis, ellaFitzgerald, billieHoliday);
    }

    public static SingleCheckGroup makeSingleCheckClassicGenre() {
        return new SingleCheckGroup("Classic", makeClassicArtists(), R.drawable.ic_violin);
    }

    public static List<Expert> makeClassicArtists() {
        Expert beethoven = new Expert("Ludwig van Beethoven", false);
        Expert bach = new Expert("Johann Sebastian Bach", true);
        Expert brahms = new Expert("Johannes Brahms", false);
        Expert puccini = new Expert("Giacomo Puccini", false);

        return Arrays.asList(beethoven, bach, brahms, puccini);
    }

    public static SingleCheckGroup makeSingleCheckSalsaGenre() {
        return new SingleCheckGroup("Salsa", makeSalsaArtists(), R.drawable.ic_maracas);
    }

    public static List<Expert> makeSalsaArtists() {
        Expert hectorLavoe = new Expert("Hector Lavoe", true);
        Expert celiaCruz = new Expert("Celia Cruz", false);
        Expert willieColon = new Expert("Willie Colon", false);
        Expert marcAnthony = new Expert("Marc Anthony", false);

        return Arrays.asList(hectorLavoe, celiaCruz, willieColon, marcAnthony);
    }

    public static SingleCheckGroup makeSingleCheckBluegrassGenre() {
        return new SingleCheckGroup("Bluegrass", makeBluegrassArtists(), R.drawable.ic_banjo);
    }

    public static List<Expert> makeBluegrassArtists() {
        Expert billMonroe = new Expert("Bill Monroe", false);
        Expert earlScruggs = new Expert("Earl Scruggs", false);
        Expert osborneBrothers = new Expert("Osborne Brothers", true);
        Expert johnHartford = new Expert("John Hartford", false);

        return Arrays.asList(billMonroe, earlScruggs, osborneBrothers, johnHartford);
    }

    public static SingleCheckGroup makeSingleCheckDr() {
        return new SingleCheckGroup("پزشک", makeDrs(), R.drawable.ic_banjo);
    }

    public static List<Expert> makeDrs() {
        Expert billMonroe = new Expert("پزشک عمومی", false);
        Expert earlScruggs = new Expert("دندانپزشک", false);
        Expert osborneBrothers = new Expert("چشم پزشک", true);
        Expert johnHartford = new Expert("علوم آزمایشگاهی", false);

        return Arrays.asList(billMonroe, earlScruggs, osborneBrothers, johnHartford);
    }
}
