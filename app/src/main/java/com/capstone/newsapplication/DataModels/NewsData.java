package com.capstone.newsapplication.DataModels;

import com.capstone.newsapplication.R;

import java.util.Arrays;
import java.util.List;

public class NewsData {
    public static List<News> getNewsList() {
        return Arrays.asList(
                new News(
                        "News 1",
                        "The specimen signature of ex-Bamban mayor Alice Guo (real name: Guo Hua Ping) did not match the signature in the counter-affidavit notarized by Atty. Elmer Galicia, according to National Bureau of Investigation Director Jaime Santiago. This has raised concerns regarding the legitimacy of the document and whether proper legal procedures were followed. Authorities are currently investigating the matter to determine the next course of action. Legal experts have weighed in, suggesting that if proven, this discrepancy could lead to serious legal repercussions for the involved parties, including the possibility of forgery charges.",
                        R.drawable.newssample2
                ),
                new News(
                        "News 2",
                        "WHO may make information on acute public health events available, if other information about the same event has already become publicly available and there is a need for the dissemination of authoritative and independent information. The DON reports fulfill this requirement. The goal is to ensure transparency and provide timely updates to both the public and health authorities about emerging threats. WHO's decision to release such reports is seen as crucial in battling misinformation and ensuring that public health responses are based on accurate and verified data. This policy has been praised for contributing to the swift global response to major health crises like the COVID-19 pandemic.",
                        R.drawable.newssample3
                ),
                new News(
                        "News 3",
                        "Rappler had previously argued the Omidyar Network was a silent investor. Omidyar later transferred its investment in Rappler to the site’s local managers to stave off efforts by Duterte to shut it down. This transfer was viewed as a strategic move to preserve Rappler's independence amid growing government scrutiny of the media outlet's operations. The case has drawn international attention, with press freedom advocates expressing concern over the Philippines’ shrinking space for independent journalism. Rappler continues to be a major voice in the fight for free speech in the region, with its CEO, Maria Ressa, receiving global recognition, including a Nobel Peace Prize.",
                        R.drawable.newssample4
                ),
                new News(
                        "News 4",
                        "It was a love that turned sour – but briefly – after controversy hit the new tourism campaign “Love the Philippines.” With President Marcos in attendance, the Department of Tourism (DOT) in late June launched the new campaign and slogan. Despite the initial excitement, the campaign faced backlash due to similarities with previous campaigns and questions about its originality. DOT officials are working to address these concerns while continuing to promote the campaign. The new slogan aims to rebrand the Philippines as a top travel destination, focusing on the country's vibrant culture, natural beauty, and the warmth of its people. The tourism department has announced plans to further develop promotional materials to align with the new slogan and expand marketing efforts globally.",
                        R.drawable.newssample5
                )
        );
    }
}
