#include "Domain.h"
#include <iostream>
#include <vector>
#include <fstream>


class StoreRepo {
protected:
	virtual std::vector<Book> read_from_file() = 0;
	virtual void write_to_file(std::vector<Book> elements) = 0;
	virtual void add(Book&) = 0;
	virtual void remove(Book&) = 0;
	virtual void update(Book&) = 0;
	virtual int search(Book&) = 0;
	virtual std::vector<Book> getAll() = 0;
	virtual int getLength() = 0;
	virtual Book get_book(std::string name) = 0;
};


class JSONRepo : public StoreRepo {

};


class CSVRepository : public StoreRepo {
public:
	std::string path;
	std::vector<Book> read_from_file() override;
	void write_to_file(std::vector<Book> books) override;
public:

	CSVRepository(std::string path);
	CSVRepository();
	CSVRepository(const CSVRepository& other);
	~CSVRepository();

	void add(Book& book) override;
	void remove(Book& book) override;
	void update(Book& book) override;
	int search(Book& book) override;
	std::vector<Book> getAll() override;
	int getLength() override;

	Book get_book(std::string name) override {
		std::vector<Book> all = this->getAll();
		for (int i = 0; i < all.size(); i++)
			if (all.at(i).getTitle() == name)
				return all.at(i);
		return Book{ "", "", "", -1, "", "" };
	}


};
