#include "UserInteface.h"
#include "tests.h"
int main(int argc, char* argv[])
{
	//all_tests();

	CSVRepository repo{ "lala.csv" };
	Service service{ repo };
	QApplication a(argc, argv);
	UserInterface w{ service };
	w.show();

	//all_tests();

	//return 0;
	return a.exec();
}
