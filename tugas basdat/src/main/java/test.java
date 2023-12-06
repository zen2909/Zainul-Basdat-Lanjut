import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.Scanner;
import java.util.logging.Level;

public class test {
    private static final String DATABASE_NAME = "tugasbasdat";
    private static final String COLLECTION_NAME = "todolist";
    String tugas;
    boolean status;

    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

    MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
    MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

    public test(){
        proses();
    }


    public void proses() {

        Scanner input = new Scanner(System.in);
        int pilih = 0;
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        do {
            System.out.println("MENU");
            System.out.println("1.Insert Data");
            System.out.println("2.Update Data");
            System.out.println("3.Delete Data");
            System.out.println("4.Read Data");
            System.out.println("5.Exit");
            System.out.print("Pilih : ");
            pilih = input.nextInt();
            input.nextLine();
            switch (pilih) {
                case 1:
                    System.out.println("Masukkan data yang ingin ditambahkan");
                    System.out.print("tugas: ");
                    tugas = input.nextLine();
                    Document newBook = new Document("tugas", tugas)
                            .append("status_selesai", false);
                    collection.insertOne(newBook);
                    System.out.println("Berhasil Menambahkan tugas");
                    break;
                case 2:
                    System.out.print("Masukkan Tugas yang selesai: ");
                    tugas = input.nextLine();
                    collection.updateOne(
                            Filters.eq("tugas", tugas),
                            Updates.combine(
                                    Updates.set("status_selesai", true)
                            )
                    );
                    System.out.println("Berhasil Update tugas.");
                    break;
                case 3:
                    System.out.print("Masukkan tugas yang ingin dihapus : ");
                    tugas = input.nextLine();
                    collection.deleteOne(Filters.eq("tugas", tugas));
                    System.out.println("Tugas berhasil dihapus");
                    break;
                case 4:
                    FindIterable<Document> documents = collection.find();
                    for (Document document : documents) {
                        tugas = document.getString("tugas");
                        status = document.getBoolean("status_selesai");

                        System.out.println("Tugas: " + tugas);
                        System.out.println("Status: " + status);
                        System.out.println("--------------");
                    }
                    break;
                case 5:
                    System.out.println("Program Dihentikan");
                    break;
            }
        } while (pilih!=5);
    }
}
