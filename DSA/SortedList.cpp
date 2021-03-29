#include "Iterator.h"


#include <iostream>
using namespace std;
#include <exception>

SortedList::SortedList(Relation r) {
	this->r = r;
	this->root = NULL; // empty list
	this->nr_elements = 0;
}

SortedList::SortedList()
{
	this->r = NULL;
	this->root = NULL;
	this->nr_elements = 0;
}

int SortedList::size() const {
	// Complexity theta(1);
	return this->nr_elements;
}

bool SortedList::isEmpty() const {
	// Complexity theta(1);
	return this->nr_elements == 0;
}

TComp SortedList::getByPosition(int pos) const {
	// Complexity BC:theta(1) AV:theta(log2n); WC:theta(log2n);
	if (0 > pos || this->nr_elements <= pos)
		throw std::exception("Invalid position");
	BTSNode* current = this->root;
	int current_position = current->poz;
	int last_position = 0;
	while (current_position != pos) {
		if (pos > current_position)
		{
			current_position += current->right->poz;
			current_position += 1;
			current = current->right;
		}
		else {
			current_position -= 1;
			current = current->left;
		}
	}

	return current->info;
}

bool SortedList::remove(TComp m) {
	if (this->root == NULL) return false;

	if (this->root->info == m) {
		// we remove the root;
		this->nr_elements--;
		if (this->root->left == NULL && this->root->right == NULL)
			this->root = NULL;
		else if (this->root->left == NULL && this->root->right != NULL)
		{
			this->root = this->root->right;
		}
		else if (this->root->left != NULL && this->root->right == NULL)
		{
			this->root = this->root->left;
		}
		else {
			// root has 2 children
			// we find the minimum from the right subtree
			BTSNode* min = this->root->right;
			BTSNode* min_parent = this->root;
			while (min->left != NULL)
			{
				min_parent = min;
				min = min->left;
			}
			// min has no left children
			// min might have a right children

			this->root->info = min->info;
			min_parent->right = min->right;
		}

		return true;
	}


	BTSNode* current = this->root;
	BTSNode* parrent = NULL;

	while (current->info != m)
	{
		parrent = current;
		if (this->r(m, current->info))
			current = current->left;
		else
			current = current->right;
		if (current == NULL)
			return false;
	}

	// we have to change the position of the nodes we traversed
	this->nr_elements--;
	BTSNode* aux = this->root;
	while (aux->info != m) {
		if (this->r(m, aux->info))
		{
			aux->poz--;
			aux = aux->left;

		}
		else
			aux = aux->right;
	}

	if (current->left == NULL && current->right == NULL) {
		if (parrent->left == current)
			parrent->left = NULL;
		else
			parrent->right = NULL;

	}
	else if (current->left != NULL && current->right != NULL) {
		// current has 2 children
		// we find the minimum from the right subtree
		BTSNode* min = current->right;
		BTSNode* min_parent = current;
		while (min->left != NULL)
		{
			min_parent = min;
			min = min->left;
		}
		// min has no left children
		// min might have a right children

		min_parent->left = min->right;
		current->info = min->info;
	}
	else if (current->left == NULL && current->right != NULL) {
		if (parrent->right == current)
			parrent->right = current->right;
		else
			parrent->left = current->right;
	}
	else {
		if (parrent->right == current)
			parrent->right = current->left;
		else
			parrent->left = current->left;
	}
	return true;
}

bool SortedList::search(TComp e) const {

	BTSNode* current = this->root;
	//std::vector<Movie> movies_found;
	while (current->info != e) {
		if (this->r(e, current->info))
			current = current->left;
		else
			current = current->right;
		if (current == NULL)
			return false;
	}

	if (current != NULL)
	{

		return true;
	}


}

void SortedList::add(TComp e) {

	if (this->root == NULL) {
		SortedList::BTSNode* node = SortedList::create_node(e, NULL, NULL, 0);
		this->root = node;
		this->nr_elements++;
		return;
	}

	BTSNode* parent = NULL;
	BTSNode* current = this->root;
	while (current->info != e || current != NULL) {
		parent = current;
		if (this->r(e, current->info))
		{
			current->poz++;
			current = current->left;
		}
		else
			current = current->right;
		if (current == NULL)
			break;
	}
	if (current != NULL) {
		// we already have the element; error;
	}
	else {
		BTSNode* node = create_node(e, NULL, NULL, 0);
		if (this->r(e, parent->info))
			parent->left = node;
		else
			parent->right = node;
		this->nr_elements++;
	}
}

ListIterator SortedList::iterator() {
	return ListIterator(*this);
}

//destructor
SortedList::~SortedList() {

}
