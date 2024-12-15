package modele;

import java.math.BigDecimal;
import java.util.Objects;



public class ProvisionCharge{
	
		private String idBail;
    	private String dateChangement;
    	private BigDecimal provisionPourCharge;
    	
    	public ProvisionCharge(String idBail, String dateChangement, BigDecimal provisionPourCharge) {
    		this.idBail = idBail;
    		this.dateChangement = dateChangement;
    		this.provisionPourCharge = provisionPourCharge;
    	}

		/**
		 * @return le dateChangement
		 */
		public String getDateChangement() {
			return dateChangement;
		}

		/**
		 * @return le provisionPourCharge
		 */
		public BigDecimal getProvisionPourCharge() {
			return provisionPourCharge;
		}

		/**
		 * @param provisionPourCharge le provisionPourCharge de provision pour charge
		 */
		public void setProvisionPourCharge(BigDecimal provisionPourCharge) {
			this.provisionPourCharge = provisionPourCharge;
		}

		/**
		 * @return the idBail
		 */
		public String getIdBail() {
			return idBail;
		}

		@Override
		public int hashCode() {
			return Objects.hash(dateChangement, idBail);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof ProvisionCharge)) {
				return false;
			}
			ProvisionCharge other = (ProvisionCharge) obj;
			return Objects.equals(dateChangement, other.dateChangement) && Objects.equals(idBail, other.idBail);
		}

		@Override
		public String toString() {
			return "ProvisionCharge [idBail=" + idBail + ", dateChangement=" + dateChangement + ", provisionPourCharge="
					+ provisionPourCharge + "]";
		}

		
		
    }