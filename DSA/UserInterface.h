#include "Iterator.h"
#include <sstream>

class UserInterface {
private:
	SortedList* sl;
public:
	UserInterface(Relation r);
	UserInterface(const UserInterface& other);
	~UserInterface();


	void add();
	void remove();
	void remove_all();
	void search();
	void print();

	void get_best_movie();
	void get_movie_by_index();

	void print_menu();
	void print_details();

	void run();

	void config();


};