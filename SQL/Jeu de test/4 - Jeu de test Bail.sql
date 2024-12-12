-- Insertion de baux valides, avec identifiant_logement existant
INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement)
VALUES ('BAI01', TO_DATE('2023-01-01', 'YYYY-MM-DD'), TO_DATE('2024-01-01', 'YYYY-MM-DD'), 'LOG001');

INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement)
VALUES ('BAI02', TO_DATE('2023-02-01', 'YYYY-MM-DD'), NULL, 'LOG002');

INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement)
VALUES ('BAI03', TO_DATE('2023-03-01', 'YYYY-MM-DD'), NULL, 'LOG003');

INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement)
VALUES ('BAI04', TO_DATE('2023-04-01', 'YYYY-MM-DD'), TO_DATE('2024-04-01', 'YYYY-MM-DD'), 'LOG001');

INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement)
VALUES ('BAI05', TO_DATE('2023-05-01', 'YYYY-MM-DD'), NULL, 'LOG002');
