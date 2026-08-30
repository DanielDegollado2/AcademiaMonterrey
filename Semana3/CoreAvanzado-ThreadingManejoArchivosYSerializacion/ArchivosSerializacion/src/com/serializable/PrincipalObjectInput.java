package com.serializable;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PrincipalObjectInput {

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		String currentDir = System.getProperty("user.dir");
		Path dataFile = Path.of(currentDir, "data", "gorillas.data");

		List<Gorilla> gorillas = readFromFile(dataFile);

		gorillas.forEach(System.out::println);

		System.out.println("Listo!!!");
	}

	static List<Gorilla> readFromFile(Path dataFile) throws IOException, ClassNotFoundException {

		var gorillas = new ArrayList<Gorilla>();

		try (var in = new ObjectInputStream(
				      new BufferedInputStream(
				      Files.newInputStream(dataFile)))) {
			while (true) {
				var object = in.readObject();
				if (object instanceof Gorilla g)
					gorillas.add(g);
			}
		} catch (EOFException e) {
			return gorillas;
		}

	}

}