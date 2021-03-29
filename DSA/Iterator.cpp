#include "Iterator.h"
#include <iostream>

using namespace std;

ListIterator::ListIterator(const SortedList& sl) : sl(sl) {
	this->nodes = new SortedList::BTSNode * [sl.nr_elements + 1];
	this->top = 0;
	SortedList::BTSNode* current = sl.root;
	while (current != NULL)
	{
		this->nodes[this->top++] = current;
		current = current->left;
	}
	this->top--;
}

void ListIterator::first() {
	this->nodes = new SortedList::BTSNode * [sl.nr_elements];
	this->top = 0;
	SortedList::BTSNode* current = sl.root;
	while (current != NULL)
	{
		this->nodes[this->top++] = current;
		current = current->left;
	}
	this->top--;
}

void ListIterator::next() {
	SortedList::BTSNode* current_node = this->nodes[this->top];

	// we will " remove " the first node of the stack , check if it has a right , if it does , we push into the stack deep on the left
	if (current_node->right != NULL) {
		this->nodes[this->top] = current_node->right;
		this->top++;
		SortedList::BTSNode* aux = current_node->right->left;
		if (aux != NULL)
		{
			while (aux != NULL)
			{
				this->nodes[this->top] = aux;
				this->top++;
				aux = aux->left;
			}
		}

	}
	this->top--;
}

bool ListIterator::valid() const {
	return this->top != -1;
}

TComp ListIterator::getCurrent() const {
	if (!this->valid())
		throw std::exception("Invalid iterator");


	if (this->nodes[this->top] != NULL)
		return this->nodes[this->top]->info;
	//else
		//return  Movie();
}


