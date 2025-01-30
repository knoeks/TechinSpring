package lt.techin.demo_2_diena.repository;

import lt.techin.demo_2_diena.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// sita visad reikia sukurt kai norime paimt is duom

// cia sukuriame interface kuris nurodys repozitorijos
// objekta Book, long ID -> juos ima pati hibernate
// ir apdirbamas kazkaip ir tada paduodamas mums per
// pacias anotacijas
public interface BookRepository extends JpaRepository<Book, Long> {
//  kol kas tuscia bet jei cia galim savo metodus sukurt kurie
//  bus naudojami services

  //jis automatiskai supranta kad tai yra knygos todel to rasyti nereikia
  List<Book> findAllByTitle(String title);

  Optional<Book> findByTitle(String title);
}
