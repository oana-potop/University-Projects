#include "Iterator.h"
#include <assert.h>
#include <time.h>

bool test_relation(TComp c1, TComp c2) {
	if (c1 <= c2) {
		return true;
	}
	else {
		return false;
	}
}

Movie create_test_movie(int rating)
{
	Movie m{ rating, "", "" };
	return m;
}

void test_add() {
	SortedList l{ test_relation };
	assert(l.isEmpty());
	for (int i = 0; i < 10; i++)
	{
		l.add(create_test_movie(i));
		l.add(create_test_movie(-i));
	}
	assert(l.size() == 20);
}

void test_remove() {
	SortedList l{ test_relation };
	for (int i = 1; i < 21; i++)
	{
		l.add(create_test_movie(i));
		l.add(create_test_movie(-i));
	}
	assert(l.size() == 40);

	for (int i = 11; i < 21; i++)
	{
		assert(l.remove(create_test_movie(i)) == true);
		assert(l.remove(create_test_movie(-i)) == true);
		assert(l.remove(create_test_movie(i)) == false);


	}

	assert(l.size() == 20);


	for (int i = 1; i < 11; i++)
	{
		assert(l.remove(create_test_movie(i)) == true);
		assert(l.remove(create_test_movie(-i)) == true);
		assert(l.remove(create_test_movie(i)) == false);
	}
	assert(l.size() == 0);


	for (int i = -100; i < 100; i++)
		assert(l.remove(create_test_movie(i)) == false);

}

void test_iterator() {
	srand(time(0));
	SortedList l{ test_relation };
	for (int i = 0; i < 100; i++)
		l.add(create_test_movie(rand() % 1000));
	int counter = 0;

	ListIterator it = l.iterator();

	while (it.valid())
	{
		counter++;
		it.getCurrent();
		it.next();
	}
	assert(!it.valid());
	it.first();
	assert(it.valid());
	assert(counter == 100);


}

void tests() {
	test_add();
	test_remove();
	test_iterator();
}