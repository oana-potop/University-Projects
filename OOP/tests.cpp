#include "Repository.h"


void all_tests()
{
	CSVRepository repo{ "test.csv" };
	Book book1{ "1","1","1",1,"1","1" };
	repo.add(book1);


}