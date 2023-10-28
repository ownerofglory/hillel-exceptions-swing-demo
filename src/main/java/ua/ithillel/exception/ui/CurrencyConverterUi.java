package ua.ithillel.exception.ui;

import ua.ithillel.exception.converter.CurrencyConverter;
import ua.ithillel.exception.exception.ClientException;
import ua.ithillel.exception.exception.ConversionException;
import ua.ithillel.exception.model.Currency;
import ua.ithillel.exception.util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CurrencyConverterUi extends JFrame {
    private final CurrencyConverter converter;

    private final JTextField sourceInput;
    private final JTextField targetInput;
    private final JComboBox<Currency> sourceMenu;
    private final JComboBox<Currency> targetMenu;
    private final JButton convertButton;
    private final JLabel statusLabel;

    {
        setSize(600, 200);
        setTitle("Hillel Currency App");
        setLayout(new GridLayout(3, 2));
        sourceInput = new JTextField();
        targetInput = new JTextField();
        sourceMenu = new JComboBox<>();
        targetMenu = new JComboBox<>();
        convertButton = new JButton("Convert");
        statusLabel = new JLabel("");

        add(sourceInput);
        add(targetInput);
        add(sourceMenu);
        add(targetMenu);
        add(convertButton);
        add(statusLabel);

        convertButton.addActionListener(this::onConvertClick);

        setVisible(true);
    }


    public CurrencyConverterUi(CurrencyConverter converter) throws HeadlessException {
        this.converter = converter;

        try {
            List<Currency> allCurrencies = converter.getAllCurrencies();

            allCurrencies.forEach(sourceMenu::addItem);
            allCurrencies.forEach(targetMenu::addItem);

        } catch (ClientException e) {
            statusLabel.setText("Unable to fetch currencies: " + e.getMessage());
        }
    }

    private void onConvertClick(ActionEvent event) {

        statusLabel.setText("");

        try {
            Currency sourceCurrency = (Currency) sourceMenu.getSelectedItem();
            Currency targetCurrency = (Currency) targetMenu.getSelectedItem();
            String sourceText = sourceInput.getText();

            double sourceValue = StringUtil.convertDouble(sourceText);

            double converted = converter.convert(sourceValue, sourceCurrency, targetCurrency);

            targetInput.setText(converted + "");
        } catch (ConversionException e) {
            statusLabel.setText("Input invalid" + e.getMessage());
        } catch (ClientException e) {
            statusLabel.setText("Client error: " + e.getMessage());
        }

    }
}
