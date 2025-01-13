
SELECT pkg_charge_calculations.diff_mois('MATLOC001', 'Eau') AS difference_mois FROM DUAL;
SELECT pkg_charge_calculations.cout_avant_division('MATLOC001', 'Eau') AS cout_avant_eau FROM DUAL;
SELECT pkg_charge_calculations.cout_apres_division('MATLOC001', 'Eau') AS cout_apres_eau FROM DUAL;



SELECT pkg_charge_calculations.diff_mois('MATLOC001', 'Electricité') AS difference_mois FROM DUAL;
SELECT pkg_charge_calculations.cout_avant_division('MATLOC001', 'Electricité') AS cout_avant_electricite FROM DUAL;
SELECT pkg_charge_calculations.cout_apres_division('MATLOC001', 'Electricité') AS cout_apres_electricite FROM DUAL;

SELECT pkg_charge_calculations.total_cout_locataire('MATLOC001') AS total_cout FROM DUAL;

