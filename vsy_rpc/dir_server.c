#include "dir.h"
#include <dirent.h>

readdir_res* readdir_1_svc(nametype* argp, struct svc_req* bla) {

  namelist nl; namelist *nlp;
  DIR *dirp;
  struct dirent *d;
  static readdir_res result;

  xdr_free((xdrproc_t) xdr_readdir_res, (char*) &result);

  dirp = opendir(*argp);
  nlp = &result.readdir_res_u.list;
  while ((d = readdir(dirp))) {
    nl = *nlp = malloc(sizeof(namenode));
    nl->name = strdup(d->d_name);
    nlp = &nl->next;
  }
  *nlp = NULL;
  result.errno = 0;
  closedir(dirp);
  return (&result);
}
