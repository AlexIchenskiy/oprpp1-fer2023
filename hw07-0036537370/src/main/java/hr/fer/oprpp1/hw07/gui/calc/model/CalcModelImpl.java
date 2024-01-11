package hr.fer.oprpp1.hw07.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementation of the calculator model.
 */
public class CalcModelImpl implements CalcModel {

    /**
     * Boolean representing whether the model is editable.
     */
    private boolean editable = true;

    /**
     * Boolean representing whether the number is positive.
     */
    private boolean positive = true;

    /**
     * String representing a number inputted by user.
     */
    private String userInput = "";

    /**
     * A number representing a user input.
     */
    private double inputValue = 0;

    /**
     * String representing a frozen input value.
     */
    private String frozenInput = null;

    /**
     * Currently active operand.
     */
    private Double activeOperand = null;

    /**
     * Currently active operator.
     */
    private DoubleBinaryOperator operator = null;

    /**
     * List of calculator model listeners.
     */
    private List<CalcValueListener> listeners = new ArrayList<>();

    /**
     * Prijava promatrača koje treba obavijestiti kada se
     * promijeni vrijednost pohranjena u kalkulatoru.
     *
     * @param l promatrač; ne smije biti <code>null</code>
     * @throws NullPointerException ako je za <code>l</code> predana vrijednost <code>null</code>
     */
    @Override
    public void addCalcValueListener(CalcValueListener l) {
        Objects.requireNonNull(l, "Listener cant be null!");
        this.listeners.add(l);
    }

    /**
     * Odjava promatrača s popisa promatrača koje treba
     * obavijestiti kada se promijeni vrijednost
     * pohranjena u kalkulatoru.
     *
     * @param l promatrač; ne smije biti <code>null</code>
     * @throws NullPointerException ako je za <code>l</code> predana vrijednost <code>null</code>
     */
    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        Objects.requireNonNull(l, "Listener cant be null!");
        this.listeners.remove(l);
    }

    /**
     * Getter for user input.
     * @return Returns the current user input value
     */
    public String getUserInput() {
        if (this.userInput.isBlank()) return "0";
        return this.positive ? this.userInput : "-" + this.userInput;
    }

    /**
     * Vraća trenutnu vrijednost koja je pohranjena u kalkulatoru.
     *
     * @return vrijednost pohranjena u kalkulatoru
     */
    @Override
    public double getValue() {
        return this.positive ? this.inputValue : Double.parseDouble("-" + this.inputValue);
    }

    /**
     * Upisuje decimalnu vrijednost u kalkulator. Vrijednost smije
     * biti i beskonačno odnosno NaN. Po upisu kalkulator
     * postaje needitabilan.
     *
     * @param value vrijednost koju treba upisati
     */
    @Override
    public void setValue(double value) {
        this.editable = false;
        this.positive = value >= 0;
        this.inputValue = Math.abs(value);
        this.userInput = Double.toString(this.inputValue);
        this.frozenInput = null;

        this.notifyListeners();
    }

    /**
     * Vraća informaciju je li kalkulator editabilan (drugim riječima,
     * smije li korisnik pozivati metode {@link #swapSign()},
     * {@link #insertDecimalPoint()} te {@link #insertDigit(int)}).
     *
     * @return <code>true</code> ako je model editabilan, <code>false</code> inače
     */
    @Override
    public boolean isEditable() {
        return this.editable;
    }

    /**
     * Resetira trenutnu vrijednost na neunesenu i vraća kalkulator u
     * editabilno stanje.
     */
    @Override
    public void clear() {
        this.userInput = "";
        this.inputValue = 0;
        this.positive = true;
        this.editable = true;

        this.notifyListeners();
    }

    /**
     * Obavlja sve što i {@link #clear()}, te dodatno uklanja aktivni
     * operand i zakazanu operaciju.
     */
    @Override
    public void clearAll() {
        this.clearActiveOperand();
        this.operator = null;
        this.frozenInput = null;
        this.clear();
    }

    /**
     * Mijenja predznak unesenog broja.
     *
     * @throws CalculatorInputException ako kalkulator nije editabilan
     */
    @Override
    public void swapSign() throws CalculatorInputException {
        if (!this.editable) throw new CalculatorInputException("Cant swap sign for uneditable model!");
        this.positive = !this.positive;
        this.frozenInput = null;

        this.notifyListeners();
    }

    /**
     * Dodaje na kraj trenutnog broja decimalnu točku.
     *
     * @throws CalculatorInputException ako nije još unesena niti jedna znamenka broja,
     *                                  ako broj već sadrži decimalnu točku ili ako kalkulator nije editabilan
     */
    @Override
    public void insertDecimalPoint() throws CalculatorInputException {
        if (!this.editable) throw new CalculatorInputException("Cant insert decimal point to an uneditable model!");
        if (this.userInput.contains(".")) throw new CalculatorInputException("Cant insert second decimal point!");
        if (this.userInput.isEmpty()) throw new CalculatorInputException("Cant insert decimal point to an empty " +
                "value!");

        String temp = this.userInput.isBlank() ? "0." : this.userInput + ".";
        this.frozenInput = null;

        try {
            this.userInput = temp;
            this.inputValue = Double.parseDouble(temp);
        } catch (Exception e) {
            throw new CalculatorInputException("New value must be parsable to double!");
        }

        this.notifyListeners();
    }

    /**
     * U broj koji se trenutno upisuje na kraj dodaje poslanu znamenku.
     * Ako je trenutni broj "0", dodavanje još jedne nule se potiho
     * ignorira.
     *
     * @param digit znamenka koju treba dodati
     * @throws CalculatorInputException ako bi dodavanjem predane znamenke broj postao prevelik za konačan prikaz u tipu {@link Double}, ili ako kalkulator nije editabilan.
     * @throws IllegalArgumentException ako je <code>digit &lt; 0</code> ili <code>digit &gt; 9</code>
     */
    @Override
    public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
        if (!this.editable) throw new CalculatorInputException("Cant input digits while calculator is not editable!");
        if (this.userInput.equals("0") && digit == 0) return;

        String temp = this.userInput.equals("0") ? "" + digit : this.userInput + digit;
        this.frozenInput = null;

        try {
            this.userInput = temp;
            this.inputValue = Double.parseDouble(temp);

            if (Double.isInfinite(this.inputValue)) throw new CalculatorInputException("Number cant be that big!");
        } catch (NumberFormatException e) {
            throw new CalculatorInputException("New value must be parsable to double!");
        }

        this.notifyListeners();
    }

    /**
     * Provjera je li upisan aktivni operand.
     *
     * @return <code>true</code> ako je aktivani operand upisan, <code>false</code> inače
     */
    @Override
    public boolean isActiveOperandSet() {
        return this.activeOperand != null;
    }

    /**
     * Dohvat aktivnog operanda.
     *
     * @return aktivni operand
     * @throws IllegalStateException ako aktivni operand nije postavljen
     */
    @Override
    public double getActiveOperand() throws IllegalStateException {
        if (!this.isActiveOperandSet()) throw new IllegalStateException("Active operand must be set to be fetched!");
        return this.activeOperand;
    }

    /**
     * Metoda postavlja aktivni operand na predanu vrijednost.
     * Ako kalkulator već ima postavljen aktivni operand, predana
     * vrijednost ga nadjačava.
     *
     * @param activeOperand vrijednost koju treba pohraniti kao aktivni operand
     */
    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = activeOperand;
        this.editable = false;

        this.notifyListeners();
    }

    /**
     * Uklanjanje zapisanog aktivnog operanda.
     */
    @Override
    public void clearActiveOperand() {
        this.activeOperand = null;

        this.notifyListeners();
    }

    /**
     * Dohvat zakazane operacije.
     *
     * @return zakazanu operaciju, ili <code>null</code> ako nema zakazane operacije
     */
    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return this.operator;
    }

    /**
     * Postavljanje zakazane operacije. Ako zakazana operacija već
     * postoji, ovaj je poziv nadjačava predanom vrijednošću.
     *
     * @param op zakazana operacija koju treba postaviti; smije biti <code>null</code>
     */
    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        this.operator = op;

        this.notifyListeners();
    }

    @Override
    public void freezeValue(String value) {
        Objects.requireNonNull(value, "Value cant be null!");
        this.frozenInput = value;

        this.notifyListeners();
    }

    @Override
    public boolean hasFrozenValue() {
        return this.frozenInput != null;
    }

    /**
     * Function to notify all listeners about changes in the model.
     */
    private void notifyListeners() {
        this.listeners.forEach(listener -> {
            listener.valueChanged(this);
        });
    }

    /**
     * Returns a string representation of the model value.
     * @return A string representation of the model value
     */
    @Override
    public String toString() {
        if (this.hasFrozenValue()) return this.frozenInput;

        String sign = this.positive ? "" : "-";

        if (this.userInput.isBlank()) return sign + "0";

        return sign + this.userInput;
    }

}
