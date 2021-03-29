#pragma once

#include <iostream>
#include <string>
#include "Domain.h"
#include <vector>
class ListIterator;
typedef Movie TComp;
typedef bool (*Relation)(TComp, TComp);



class SortedList {
protected:
	typedef struct BTSNode {
		TComp info;
		BTSNode* left;
		BTSNode* right;
		int poz;
	}BTSNode;

	BTSNode* create_node(TComp info, BTSNode* left, BTSNode* right, int poz) {
		BTSNode* node = new BTSNode{};
		node->info = info;
		node->left = left;
		node->right = right;
		node->poz = poz;
		return node;
	}
private:
	friend class ListIterator;
private:
	BTSNode* root;
	int nr_elements;
	Relation r;

public:

	SortedList(Relation r);
	SortedList();
	~SortedList();

	void add(TComp e);
	bool remove(TComp m);
	int size() const;
	bool isEmpty() const;
	// we will consider the 0 <= pos < this->size(); // pos begin at 0;
	TComp getByPosition(int pos) const;
	bool search(TComp e) const;
	ListIterator iterator();

};
