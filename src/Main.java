import DataBase.Database;
import Model.Product;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    private static Database database = new Database();
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        //TODO сделано так, так как сейчас конфликт имен, нельзя использовать относительный путь
        database.setDBURL("c:/users/gleb/ideaprojects/test/DataBase/products");
        try {
            database.connect();
            database.creatTable();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL !" + e.getMessage());
            database.closeConnection();
        }
        catch (ClassNotFoundException e){
            System.out.println("Не найдена библиотека jdbc");
            database.closeConnection();
        }
        boolean workFlag = true;
        String userLine = "";
        String welcomMessage = "Для выхода нажмите :q\nДля создания нового продукта 1.\nВывести продукты 2\nИзменить текущий продукт 3\nУдалиь продукт 4";

        while (workFlag){
            System.out.println(welcomMessage);
            userLine = in.nextLine();
            switch (userLine){
                case ":q": {
                    System.out.println("Досвидания");
                    workFlag = false;
                    database.closeConnection();
                }break;
                case "1":{
                    System.out.println("Добавление нового продукта");
                    createProduct();
                }break;
                case "2":{
                    System.out.println("Вывод продукта");
                    getProduct();
                }break;
                case "3":{
                    System.out.println("Редактирование продукта");
                    changeProduct();
                }break;
                case "4":{
                    System.out.println("Удаление продукта");
                    deleteProduct();
                }
            }
        }
    }

    private static void createProduct(){
        System.out.println("Введите имя продукта и количество");

        boolean flag = true;
        int count = 0;

        while(flag){
            try {
                String userLine = in.nextLine();
                var splitString = userLine.split(" ");

                count = Integer.parseInt(splitString[1]);
                String name = splitString[0];
                Product product = new Product(count, name);
                database.setProduct(product);
                flag = false;
            }
            catch (NumberFormatException e){
                System.out.println("Количество продукта введено не числом, повторите попытку.");
            }
            catch (SQLException e) {
                System.out.println("Ошибка sql " + e.getMessage());
            }
            catch (NullPointerException e) {
                System.out.println("Запрошен отсутствубщий товар ");
            }
        }
    }

    private static void getProduct() {
        System.out.println("1 Вывод всех продуктов.\n2 Вывод продуктов в которых товара больше n");
        try {
            boolean flag = true;
            while (flag) {
                switch (in.nextLine()) {
                    case "1": {
                        database.getAllTable();
                        flag = false;
                    }
                    break;
                    case "2": {
                        System.out.println("Введите количество");
                        int count = readInt();
                        database.getByCount(count);
                        flag = false;
                    }
                    break;
                    default:
                        System.out.println("Введена не известаня команда");
                }
            }
        }catch (SQLException e) {
            System.out.println("ошибка в запросе sql " + e.getMessage());
        }
    }

    //TODO в commna-lang взять isNumber
    //Функция читает с консоли значение и проверяет его на инт, при ошибки выводится min Int
    private static int readInt() {
        int tmp = Integer.MIN_VALUE;
        try {
            String userLine = in.nextLine();
            tmp = Integer.parseInt(userLine);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка, введено не целочисленное значение");
        }
        finally {
            return tmp;
        }
    }

    private static void changeProduct() {
        try {
            database.getAllTable();
            System.out.println("Выберете номер продукта для изменения");
            int id = readInt();

            System.out.println("Напишите новое количество товара");
            int count = readInt();

            database.updateProductCount(count, id);
        } catch (SQLException e) {
            System.out.println("ошибка в запросе sql " + e.getMessage());
        }
    }

    private static void deleteProduct() {
        try {
            database.getAllTable();
            System.out.println("Выберете номер продукта для удаления");
            int id = readInt();
            database.deleteProduct(id);
        } catch (SQLException e) {
            System.out.println("ошибка в запросе sql " + e.getMessage());
        }
    }

}
