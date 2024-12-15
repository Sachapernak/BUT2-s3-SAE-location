package modele;

import java.math.BigDecimal;
import java.util.Objects;




public class Regularisation{
		private String idBail;
		private String dateRegu;
		private BigDecimal montant;

		public Regularisation(String idBail, String dateRegu, BigDecimal montant) {
			this.idBail = idBail;
			this.dateRegu = dateRegu;
			this.montant = montant;
		}
		
		/**
		 * @return le dateRegu
		 */
		public String getDateRegu() {
			return dateRegu;
		}

		/**
		 * @param dateRegu the dateRegu du regu
		 */
		public void setDateRegu(String dateRegu) {
			this.dateRegu = dateRegu;
		}

		/**
		 * @return le montant
		 */
		public BigDecimal getMontantRegu() {
			return montant;
		}

		/**
		 * @param montant le montant du regu
		 */
		public void setMontant(BigDecimal montant) {
			this.montant = montant;
		}

		
		

		/**
		 * @param idBail the idBail to set
		 */
		public void setIdBail(String idBail) {
			this.idBail = idBail;
		}

		@Override
		public int hashCode() {
			return Objects.hash(dateRegu, idBail);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Regularisation)) {
				return false;
			}
			Regularisation other = (Regularisation) obj;
			return Objects.equals(dateRegu, other.dateRegu) && Objects.equals(idBail, other.idBail);
		}

		public String getIdBail() {
			return idBail;
		}

		@Override
		public String toString() {
			return "Regularisation [idBail=" + idBail + ", dateRegu=" + dateRegu + ", montant=" + montant + "]";
		}
		
		

	}