#include <stdio.h>
#include <rpc/rpc.h>
#include "dir.h"

int main(int argc, char* argv[]) {
  char* server = argv[1];
  char* dirname = argv[2];
  CLIENT *clnt;
  namelist nl;

  if ((clnt = clnt_create(server, DIRPROG, DIRVERS, "udp")) == NULL) {
    clnt_pcreateerror(server);
    exit(-1);
  }
  readdir_res *result_1 = readdir_1(&dirname, clnt);
  if (result_1 == NULL) {
    clnt_perror(clnt, "call failed");
  } else {
    for (nl = result_1->readdir_res_u.list;
          nl != NULL;
          nl = nl->next) {
      printf("%s\n", nl->name);
    }
  }

  exit(0);
}
