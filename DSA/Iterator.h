#pragma once
#include "SortedList.h"


class ListIterator {
	friend class SortedList;
private:
	const SortedList& sl;
	ListIterator(const SortedList& sl);

	SortedList::BTSNode** nodes;
	int top;

public:
	void first();
	void next();
	bool valid() const;
	TComp getCurrent() const;
};


