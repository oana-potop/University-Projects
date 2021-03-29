#include "UserInterface.h"
#include "tests.h";



bool relation_descending(TComp c1, TComp c2) {
	if (c1 >= c2) {
		return true;
	}
	else {
		return false;
	}
}

int main() {

	tests();
	UserInterface user_interface(relation_descending);
	user_interface.config();
	user_interface.run();
}