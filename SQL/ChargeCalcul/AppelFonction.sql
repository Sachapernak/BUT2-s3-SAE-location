SELECT total_cout_locataire('MATLOC001') AS total_cout FROM DUAL;

SELECT calculer_difference_mois('MATLOC001', 'Eau') AS difference_mois FROM DUAL;
SELECT cout_total_avant_division('MATLOC001', 'Eau') AS cout_total_avant FROM DUAL;
--SELECT cout_total_apres_division('MATLOC001', 'Eau') AS cout_total_apres FROM DUAL;

SELECT calculer_difference_mois('MATLOC001', 'Electricité') AS difference_mois FROM DUAL;
SELECT cout_total_avant_division('MATLOC001', 'Electricité') AS cout_total_avant FROM DUAL;
--SELECT cout_total_apres_division('MATLOC001', 'Electricité') AS cout_total_apres FROM DUAL;



