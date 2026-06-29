# Planificator Zilnic – Aplicație Web Spring Boot
Acest proiect reprezintă aplicația realizată pentru disciplina Programare Java. 
Scopul proiectului este dezvoltarea unei aplicații web care permite gestionarea taskurilor zilnice, folosind tehnologiile studiate la curs și laborator.
Proiectul este în dezvoltare și va fi extins treptat cu funcționalități noi.
## Structura proiectului
controller – controlează rutele și logica de accesare a paginilor
service – conține logica aplicației
repository – comunică cu baza de date
model – entitățile aplicației
templates – paginile HTML (Thymeleaf)
static – resurse statice (CSS, JS)
## Funcționalități existente
-structură completă de proiect Spring Boot

-configurare Maven

-configurare pachete (controller, service, repository, model)

-pagini HTML în templates

-configurare conexiune la baza de date (MySQL)

-logica de bază pentru gestionarea taskurilor

## Mod de utilizare
1. Clonarea proiectului
Utilizatorul poate descărca proiectul folosind comanda:

Cod
git clone https://github.com/BDenisa20/planificator-zilnic.git

2. Configurarea bazei de date
În fișierul application.properties se setează:
numele bazei de date

utilizatorul MySQL

parola MySQL

Exemplu:

Cod
spring.datasource.url=jdbc:mysql://localhost:3306/planificator
spring.datasource.username=root
spring.datasource.password=root

3. Rularea aplicației
Aplicația se pornește din clasa:

Cod
PlanificatorZilnicApplication.java
sau din terminal:

Cod
mvn spring-boot:run

4. Accesarea aplicației
După pornire, aplicația este disponibilă la:

Cod
http://localhost:8081

## Status proiect
Proiectul este în dezvoltare, fiind extins treptat pentru a nu rămâne doar un proiect de facultate, ci pentru a evolua într-o aplicație mai complexă, similară celor folosite în mediul profesional.

README-ul va fi actualizat pe măsură ce sunt adăugate noi funcționalități.
## Îmbunătățiri planificate

Proiectul va fi extins în versiunile următoare pentru a se apropia de o aplicație enterprise. Printre direcțiile de dezvoltare se află:

-organizarea taskurilor pe proiecte

-crearea unei secțiuni de administrare pentru utilizatori și proiecte

-implementarea unor statistici și grafice pentru vizualizarea progresului

-îmbunătățirea interfeței și a experienței de utilizare

-validări avansate pentru formularele aplicației

-adăugarea testelor unitare

-extinderea documentației pe măsură ce proiectul evoluează

Această secțiune va fi actualizată pe parcurs, odată cu adăugarea noilor funcționalități.

## Utilizarea instrumentelor AI în dezvoltarea proiectului
În realizarea acestui proiect au fost folosite instrumente AI ca suport în procesul de dezvoltare.
Acestea au fost utilizate pentru:

-clarificarea unor concepte din Spring Boot

-generarea unor exemple de cod care au fost ulterior adaptate manual

-rezolvarea erorilor apărute pe parcurs

-sugestii privind organizarea pachetelor și structurii proiectului

-redactarea documentației

Instrumentele AI au avut rol de asistență tehnică și documentare.
Implementarea finală a codului și logica aplicației au fost realizate manual.

## Tehnologii utilizate

Java 17

Spring Boot

Spring MVC

Spring Data JPA

MySQL

Thymeleaf

Maven
