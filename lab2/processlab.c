/* AUTHORS
Hreiðar Ólafur Arnarsson, hreidara14@ru.is
Maciej Sierzputowski, maciej15@ru.is
*/
#include <stdio.h>
#include <string.h>
#include <unistd.h>

int main(int argc, char const *argv[]) {
    puts("----------------------------------");
    puts("| Welcome to lab 2 - Process lab |");
    puts("----------------------------------");
    puts("Made in January 2017 by");
    puts("Hreiðar Ólafur Arnarsson, hreidara14@ru.is");
    puts("Maciej Sierzputowski, maciej15@ru.is");
    puts("");

    int status = 0;
    pid_t pid1 = fork();
    printf("Starting fork #1, process id: %i\n", pid1);

    puts("Starting program");

    pid_t pid2 = fork();
    printf("Starting fork #2, process id: %i\n", pid2);

    if(pid1 != 0) {
        printf("pid1: Waiting for process id: %i\n", pid1);
        waitpid(pid1, &status, 0);
    }

    int numberOfLoops = 100000000; // 100 million
    int i; // for the for loop
    int totallyLooped = 0;
    puts("Starting for-loop");
    for (i = 0; i < numberOfLoops; i++) {
        totallyLooped++;
    }
    printf("Finished running for-loop %i times\n", totallyLooped);

    if(pid2 != 0) {
        printf("pid2: Waiting for process id: %i\n", pid2);
        waitpid(pid1, &status, 0);
    }

    puts("Ending program");

    /*if(pid1 != 0) {
        waitpid(pid1, &status, 0);
    }*/


    /* For fun purposes, misunderstanding of first part of program */
    /*if (strcmp(argv[1], "story") == 0) {
        puts("*************************************************");
        puts("* THE STORY OF THE MOON'S PROMISE TO THE PRINCE *");
        puts("*************************************************");
        puts("Once up on a time there was a person.");
        puts("That person was a man and he dreamt of the universe");
        puts("He was also a prince. One day the king and queen");
        puts("told him that he needed to get married. He asked them:");
        puts("\"Do I have to?\"");
        puts("They told him that he had to.");
        puts("\"Can I choose my bride?\" he asked hopefully.");
        puts("They told him yes but the bride had to be a princess.");
        puts("So that night the prince looked up into the sky and saw the moon.");
        puts("\"Moon, will you help me?\" he shouted to it.");
        puts("The moon nodded and boomed to him");
        puts("\"You must wait, sad prince, and I shall send to you a princess.");
        puts("She shall be the most beautiful princess in the universe,");
        puts("a star that shines the most in the vast darkness of the void.\"");
        puts("\"I shall wait, moon! Thank you!\" the prince shouted back.");
        puts("The prince waited for a day and no princess.");
        puts("He waited for another day");

        int i; // for the for loop

        for (i = 0; i < numberOfLoops; i++) {*/
            /*printf("and another ");*/
            /* same letting the program tell us how often it has gone through a loop */
            /*if ( i > 0 && i % 1000000 == 0 ) {
                printf("and he waited for %i days\n", i);
            }
        }
        printf("and he waited for %i days.\n", numberOfLoops);
        puts("At this point in the story the prince is long dead.");
        puts("He was also extremely insane and though he claimed to be royalty");
        puts("no one believed him other than the other inmates at the asylum.");
        puts("To this day he still waits for his princess.");
        puts("Along with all the other inmates at the asylum.");
        puts("");
    }*/
    return 0;
}
