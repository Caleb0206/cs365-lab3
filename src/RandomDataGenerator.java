import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RandomDataGenerator {
    private static final int ONE_THOUSAND = 1000;
    private static final int ONE_HUNDRED = 100;
    private static final int TWO_THOUSAND = 2000;
    private static final String[] CARD_TYPES = {"Visa", "MC", "American_Express", "Discover"};
    private static String randomDate(int startYear, int endYear) {
        Random random = new Random();
        int year = startYear + random.nextInt(endYear - startYear + 1);
        int month = 1 + random.nextInt(12);
        int day = 1 + random.nextInt(28);
        return String.format("%04d-%02d-%02d", year, month, day);
    }


    public static void main(String[] args) throws IOException {
        Random random = new Random();
//        Map<Integer, List<Integer>> ownershipMap;
        RandomDataGenerator cab = new RandomDataGenerator();

//        cab.createCustomerFile(random);
//        cab.createCreditCardFile(random);
//        ownershipMap = cab.createOwnershipFile(random);
//        cab.createVendorFile(random);
//        cab.createTransactionFile(ownershipMap, random);
        cab.createPaymentFile(random);


    }
    public void createCustomerFile(Random random) throws IOException
    {
        FileWriter customerFile = new FileWriter("Customer.sql");
        for(int i = 1; i <= ONE_THOUSAND; i++)
        {
            String ssn = String.format("%03d%02d%04d",
                    random.nextInt(1000), random.nextInt(1000), random.nextInt(1000));
            String name = "Customer" + i;
            String address = "Address" + i;
            String phone = String.format("408-%03d-%04d", random.nextInt(1000), random.nextInt(1000));
            customerFile.write(String.format("insert into Customer (id, ssn, name, address, phone_number)" +
                    " values (%d, '%s', '%s', '%s', '%s');\n", i, ssn, name, address, phone));
        }
        customerFile.close();
    }
    public void createCreditCardFile(Random random) throws IOException
    {
        FileWriter cardFile = new FileWriter("CreditCard.sql");
        for(int i = 1; i <= ONE_THOUSAND; i++)
        {
            String type = CARD_TYPES[random.nextInt(CARD_TYPES.length)];
            double limit = 1000 + random.nextInt(9000);
            double balance = random.nextDouble() * limit;
            boolean active = random.nextBoolean();
            cardFile.write(String.format("insert into CreditCard (number, type, credit_limit, balance, active)" +
                    " values (%d, '%s', %.2f, %.2f, %b);\n", i, type, limit, balance, active));
        }
        cardFile.close();
    }
    public void createVendorFile(Random random) throws IOException
    {
        FileWriter vendorFile = new FileWriter("Vendor.sql");
        for(int i = 1; i <= ONE_HUNDRED; i++)
        {
            String name = "Vendor" + i;
            String location = "Location" + i;
            vendorFile.write(String.format("insert into Vendor(name, location) values ('%s', '%s');\n", name, location));
        }
        vendorFile.close();
    }
    public Map<Integer, List<Integer>> createOwnershipFile(Random random) throws IOException
    {
        FileWriter ownershipFile = new FileWriter("Ownership.sql");
        Map<Integer, List<Integer>> customerCardMap = new HashMap<>();
        Map<Integer, Integer> cardOwnershipCount = new HashMap<>();

        for (int i = 1; i <= ONE_THOUSAND; i++)
        {
            int numCards = 1 + random.nextInt(3); // 1 - 3
            Set<Integer> assignedCards = new HashSet<>();
            for (int j = 0; j < numCards; j++)
            {
                int cardNumber;
                do {
                    cardNumber = 1 + random.nextInt(ONE_THOUSAND);
                } while (assignedCards.contains(cardNumber) || cardOwnershipCount.getOrDefault(cardNumber, 0) >= 2);

                assignedCards.add(cardNumber);
                cardOwnershipCount.put(cardNumber, cardOwnershipCount.getOrDefault(cardNumber, 0) + 1);
                ownershipFile.write(String.format("insert into Ownership(customer_id, card_number, is_current) " +
                        "values (%d, %d, TRUE);\n", i, cardNumber));

                customerCardMap.computeIfAbsent(i, k -> new ArrayList<>()).add(cardNumber);
            }
        }
        ownershipFile.close();
        return customerCardMap;
    }
    public void createTransactionFile(Map<Integer, List<Integer>> ownershipMap, Random random) throws IOException
    {
        FileWriter transactionFile = new FileWriter("Transaction.sql");
        for(int i = 1; i <= TWO_THOUSAND; i++)
        {
            String date = randomDate(2020, 2025);
            int customerId = 1 + random.nextInt(ONE_THOUSAND);
            List<Integer> ownedCards = new ArrayList<>(ownershipMap.get(customerId));

            int cardNumber = ownedCards.get(random.nextInt(ownedCards.size()));
            int vendorId = 1 + random.nextInt(ONE_HUNDRED);
            double amount = 1 + random.nextDouble() * 400;
            transactionFile.write(String.format("insert into Transaction(date, customer_id, card_number, vendor_id, amount) " +
                    "values ('%s', %d, %d, %d, %.2f);\n", date, customerId, cardNumber, vendorId, amount));
        }
        transactionFile.close();
    }
    public void createPaymentFile(Random random) throws IOException {
        FileWriter paymentFile = new FileWriter("Payment.sql");
        for(int i = 1; i <= ONE_THOUSAND; i++)
        {
            double amount = 1 + random.nextDouble() * 1000;
            String date = randomDate(2020, 2025);
            paymentFile.write(String.format("insert into Payment(date, card_number, amount) " +
                    "values ('%s', %d, %.2f);\n", date, i, amount));
        }

        paymentFile.close();
    }


}
