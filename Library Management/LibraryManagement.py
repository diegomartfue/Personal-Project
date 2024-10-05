import csv
from datetime import datetime, timedelta

# Author class to represent authors
class Author:
    def __init__(self, name, birth_year):
        self.name = name
        self.birth_year = birth_year
        self.books = set()

    def add_book(self, book):
        if book not in self.books:
            self.books.add(book)

# Book class to represent books
class Book:
    def __init__(self, isbn, title, author, year, copies, genre):
        self.isbn = isbn
        self.title = title
        self.author = author
        self.year = year
        self.copies = copies
        self.available_copies = copies
        self.genre = genre

    def __str__(self):
        return f"{self.title} by {self.author} {self.year}"

# Customer class to represent customers
class Customer:
    def __init__(self, customer_id, name, email):
        self.customer_id = customer_id
        self.name = name
        self.email = email
        self.borrowed_books = {}

    def borrow_book(self, book):
        if book not in self.borrowed_books:
            self.borrowed_books[book] = datetime.today()
            return True
        return False

    def return_book(self, book):
        if book in self.borrowed_books:
            del self.borrowed_books[book]
            print(f"{self.name} returned {book.title}.")
        else:
            print(f"{self.name} did not borrow {book.title}.")

    def get_borrowed_books(self):
        return self.borrowed_books

# Library Management System class
class LibraryManagementSystem:
    def __init__(self):
        self.books = {}
        self.authors = {}
        self.customers = {}
        self.genre_classification = {}
        self.waitlist = {}
        self.next_customer_id = 1
        self.borrow_records = {}

    def load_books_from_csv(self, filename):
        try:
            with open(filename, newline='', encoding='utf-8') as csvfile:
                reader = csv.DictReader(csvfile)
                for row in reader:
                    self.add_book(
                        isbn=row['ISBN'],
                        title=row['Title'],
                        author_name=row['Author Name'],
                        author_birth_year=int(row['Author Birth Year']),
                        year=int(row['Year']),
                        copies=int(row['Copies']),
                        genre=row['Genre']
                    )
            print(f"Books loaded successfully from {filename}")
        except FileNotFoundError:
            print(f"Error: File '{filename}' not found.")
        except csv.Error as e:
            print(f"Error reading CSV file: {e}")
        except ValueError as e:
            print(f"Error in data format: {e}")
        except KeyError as e:
            print(f"Error: Missing column in CSV file: {e}")
        except Exception as e:
            print(f"An unexpected error occurred: {e}")

    def add_book(self, isbn, title, author_name, author_birth_year, year, copies, genre):
        new_book = Book(isbn, title, author_name, year, copies, genre)

        if author_name not in self.authors:
            new_author = Author(author_name, author_birth_year)
            self.authors[author_name] = new_author
        else:
            new_author = self.authors[author_name]

        new_author.add_book(new_book)
        self.books[isbn] = new_book

        if genre not in self.genre_classification:
            self.genre_classification[genre] = set()
        self.genre_classification[genre].add(isbn)

        print(f"Added '{title}' by {author_name} to the library.")

    def register_customer(self, name, email):
        customer_id = self.next_customer_id
        new_customer = Customer(customer_id, name, email)
        self.customers[customer_id] = new_customer
        self.next_customer_id += 1
        print(f"Registered customer {name} with ID: {customer_id}")
        return customer_id

    def borrow_book(self, isbn, customer_id):
        if isbn not in self.books:
            print("Error: Book not found.")
            return False
        if customer_id not in self.customers:
            print("Error: Customer not found.")
            return False

        book = self.books[isbn]
        customer = self.customers[customer_id]

        if book.available_copies > 0:
            if customer.borrow_book(book):
                book.available_copies -= 1
                print(f"{customer.name} has borrowed '{book.title}'.")
                return True
            else:
                print(f"{customer.name} has already borrowed '{book.title}'.")
        else:
            print(f"Sorry, '{book.title}' is currently unavailable.")
        return False

    def return_book(self, isbn, customer_id):
        if isbn not in self.books:
            print("Error: Book not found.")
            return False
        if customer_id not in self.customers:
            print("Error: Customer not found.")
            return False

        book = self.books[isbn]
        customer = self.customers[customer_id]

        if book in customer.borrowed_books:
            book.available_copies += 1
            customer.return_book(book)
            print(f"{customer.name} has returned '{book.title}'.")
            return True
        else:
            print(f"Error: {customer.name} has not borrowed '{book.title}'.")
            return False

    def search_books(self, query):
        results = []
        query = query.lower()
        for book in self.books.values():
            if (query in book.title.lower() or
                query in book.author.lower() or
                query == book.isbn):
                results.append(book)
        return results

    def display_available_books(self):
        available_books = [book for book in self.books.values() if book.available_copies > 0]
        return available_books

    def display_customer_books(self, customer_id):
        if customer_id not in self.customers:
            print("Error: Customer not found.")
            return []
        return self.customers[customer_id].borrowed_books

    def recommend_books(self, customer_id):
        if customer_id not in self.customers:
            print("Error: Customer not found.")
            return []

        customer = self.customers[customer_id]
        borrowed_genres = set(book.genre for book in customer.borrowed_books)
        recommended_books = []

        for genre in borrowed_genres:
            for isbn in self.genre_classification.get(genre, []):
                book = self.books[isbn]
                if book not in customer.borrowed_books and book.available_copies > 0:
                    recommended_books.append(book)
                    if len(recommended_books) == 5:
                        return recommended_books

        return recommended_books

    def add_to_waitlist(self, isbn, customer_id):
        if isbn not in self.books:
            print("Error: Book not found.")
            return False
        if customer_id not in self.customers:
            print("Error: Customer not found.")
            return False

        if isbn not in self.waitlist:
            self.waitlist[isbn] = []

        if customer_id not in self.waitlist[isbn]:
            self.waitlist[isbn].append(customer_id)
            print(f"Added customer {customer_id} to the waitlist for book {isbn}.")
            return True
        else:
            print(f"Customer {customer_id} is already on the waitlist for book {isbn}.")
            return False

    def check_late_returns(self, days_threshold=14):
        late_return_books = []

        for customer in self.customers.values():
            for book, borrow_date in customer.get_borrowed_books().items():
                if (datetime.today() - borrow_date).days > days_threshold:
                    overdue_days = (datetime.today() - borrow_date).days
                    late_return_books.append((customer, book, overdue_days))

        return late_return_books

    def run(self):
        print("Welcome to the Library Management System!")

        while True:
            print("\nLibrary Management System")
            print("1. Add Book")
            print("2. Register Customer")
            print("3. Borrow Book")
            print("4. Return Book")
            print("5. Search Books")
            print("6. Display Available Books")
            print("7. Display Customer's Borrowed Books")
            print("8. Recommend Books")
            print("9. Check Late Returns")
            print("10. Exit")

            choice = input("Enter your choice (1-10): ")

            if choice == '1':
                isbn = input("Enter ISBN: ")
                title = input("Enter title: ")
                author_name = input("Enter author name: ")
                author_birth_year = int(input("Enter author birth year: "))
                year = int(input("Enter publication year: "))
                copies = int(input("Enter number of copies: "))
                genre = input("Enter genre: ")
                self.add_book(isbn, title, author_name, author_birth_year, year, copies, genre)
            elif choice == '2':
                name = input("Enter customer name: ")
                email = input("Enter customer email: ")
                self.register_customer(name, email)
            elif choice == '3':
                isbn = input("Enter ISBN of the book to borrow: ")
                customer_id = int(input("Enter customer ID: "))
                self.borrow_book(isbn, customer_id)
            elif choice == '4':
                isbn = input("Enter ISBN of the book to return: ")
                customer_id = int(input("Enter customer ID: "))
                self.return_book(isbn, customer_id)
            elif choice == '5':
                query = input("Enter title, author, or ISBN to search: ")
                results = self.search_books(query)
                if results:
                    print("Search Results:")
                    for book in results:
                        print(book)
                else:
                    print("No books found.")
            elif choice == '6':
                available_books = self.display_available_books()
                if available_books:
                    print("Available Books:")
                    for book in available_books:
                        print(book)
                else:
                    print("No books available.")
            elif choice == '7':
                customer_id = int(input("Enter customer ID: "))
                borrowed_books = self.display_customer_books(customer_id)
                if borrowed_books:
                    print(f"Customer {customer_id} has borrowed the following books:")
                    for book, borrow_date in borrowed_books.items():
                        print(f"{book.title} (Borrowed on {borrow_date.strftime('%Y-%m-%d')})")
                else:
                    print(f"Customer {customer_id} has not borrowed any books.")
            elif choice == '8':
                customer_id = int(input("Enter customer ID: "))
                recommended_books = self.recommend_books(customer_id)
                if recommended_books:
                    print("Recommended Books:")
                    for book in recommended_books:
                        print(book)
                else:
                    print("No recommendations available.")
            elif choice == '9':
                late_return_books = self.check_late_returns()
                if late_return_books:
                    print("Late Returns:")
                    for customer, book, overdue_days in late_return_books:
                        print(f"{customer.name} has {book.title} overdue by {overdue_days} days.")
                else:
                    print("No late returns.")
            elif choice == '10':
                print("Exiting the system.")
                break
            else:
                print("Invalid choice, please try again.")

if __name__ == "__main__":
    library_system = LibraryManagementSystem()

    # Load books from CSV file
    library_system.load_books_from_csv('Books.csv')

    # Start the system
    library_system.run()
