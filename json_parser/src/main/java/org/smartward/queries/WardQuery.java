package org.smartward.queries;

import org.smartward.parser.ParserForStreamAPI;
import org.smartward.people.Doctor;
import org.smartward.people.Nurse;
import org.smartward.people.Patient;
import org.smartward.people.Person;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WardQuery {

    //result collectors
    private List<Patient> allPatients = new ArrayList<>();
    private List<Doctor> allDoctors = new ArrayList<>();
    private Map<Integer, List<Nurse>> allNursesGroupByWardNumber = new HashMap<>();

    public List<Patient> getAllPatients() {
        return allPatients;
    }

    public List<Doctor> getAllDoctors() {
        return allDoctors;
    }

    public Map<Integer, List<Nurse>> getAllNursesGroupByWardNumber() {
        return allNursesGroupByWardNumber;
    }

    //printing result
    public void printResults()
    {
        System.out.println("\nPatients");
        allPatients
                .stream()
                .map( patient -> patient.getFirstName() + " " + patient.getFamilyName())
                .forEach(System.out::println);


        System.out.println("\nDoctors");
        allDoctors
                .stream()
                .map(doctor -> doctor.getFirstName() + " " + doctor.getFamilyName() + " : " + doctor.getPagerNumber())
                .forEach(System.out::println);

        System.out.println("\nNurses");
        allNursesGroupByWardNumber.forEach((ward, nurses) -> {
            System.out.println("[Ward " + ward + "]");
            nurses
                    .stream()
                    .map(nurse -> nurse.getFirstName() + " " + nurse.getFamilyName())
                    .forEach(System.out::println);
        });
    }

    /*
     * parse and process the json at runtime using stream, extract each class type separately
     * and process each class type independently to accumulate result sets. exploiting parallel streams
     */
    public  void ExecuteWithParallelStreams(InputStream input) throws Exception
    {
        Stream.generate(new ParserForStreamAPI(input))
                .takeWhile(p -> p.isPresent())
                .map(p->p.get())
                .collect(Collectors.groupingBy( Object::getClass ))
                .values()
                .parallelStream()
                .forEach( people -> {
                    var aClass = people.get(0).getClass();  //get class back from list first item

                    if (aClass == Patient.class) {          // if list is containing patients
                        allPatients = people
                                .stream()
                                .map(person -> (Patient) person)
                                .sorted(Comparator.comparing(Person::getFirstName))
                                .sorted(Comparator.comparing(Person::getFamilyName))
                                .collect(Collectors.toList());
                    } else if (aClass == Doctor.class) {
                        allDoctors = people
                                .stream()
                                .map(person -> (Doctor) person)
                                .sorted(Comparator.comparing(Person::getFirstName))
                                .sorted(Comparator.comparing(Person::getFamilyName))
                                .collect(Collectors.toList());       //collect the result in list
                    } else if (aClass == Nurse.class) {
                        allNursesGroupByWardNumber = people
                                .stream()
                                .map(person -> (Nurse) person)
                                .collect(Collectors.groupingBy(Nurse::getWardNumber))
                                .values()
                                .parallelStream()
                                .collect( Collectors.toMap(
                                                nurses -> nurses.get(0).getWardNumber(),    //collect in map for each ward
                                                nurses -> nurses
                                                        .stream()
                                                        .sorted(Comparator.comparing(Person::getFirstName))
                                                        .sorted(Comparator.comparing(Person::getFamilyName))
                                                        .collect(Collectors.toList())
                                        )
                                );
                    }
                });

    }

}
