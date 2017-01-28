
#include <fcntl.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <dirent.h>

int main(int argc, char *argv[])
{
  puts("kaha@ru.is made this program");
  char fileName[256];
  char isFile = 1;

  if(argc > 1)
  {
    strcpy(fileName, argv[1]);
  }
  else
  {
    strcpy(fileName, ".");
  }
  int file = open(fileName, O_RDONLY);
  printf("File descriptor:%4i\n", file);
  if(file == -1)
  {
    int errornum = errno;
    printf("error:%8i\n", errornum);
    if(errornum == 2)
    {
      printf("No such file or directory: ");
      puts(fileName);
      isFile = 0;
    }
  }
  else
  {
    puts("no error");
  }

  if(!isFile) {return 0;}

  char buffer[1024];
  int bytesRead = read(file, buffer, 1024);
  printf("Bytes read:%4i\n", bytesRead);
  if(bytesRead == -1)
  {
    int errornum = errno;
    printf("error:%8i\n", errornum);
    if(errornum == 9)
    {
      puts("There is no file to read.");
    }
    if(errornum == 21)
    {
      char txt[256];
      strcpy(txt, fileName);
      puts(strcat(txt, " is a directory."));
      isFile = 0;
    }
  }
  else
  {
    buffer[bytesRead-1] = '\0';
    puts(buffer);
  }
  int closed = close(file);

  if(closed == -1)
  {
    int errornum = errno;
    printf("error:%8i\n", errornum);
  }
  else {puts("file closed");}

  if(!isFile)
  {
    char txt[256] = "working with the directory ";
    puts(strcat(txt, fileName));

    DIR *directory = opendir(fileName);
    if(directory != NULL)
    {
      puts("Directory open.");

      puts("\n");
      struct dirent *directoryEntry = readdir(directory);
      while(directoryEntry != NULL)
      {
        puts(directoryEntry->d_name);
        directoryEntry = readdir(directory);
      }
      puts("\n");
    }

    if(closedir(directory) == -1)
    {
      int errornum = errno;
      printf("error:%8i\n", errornum);
    }
    else
    {
      puts("Directory closed.");
    }
  }
  puts("kaha@ru.is made this program");
}
