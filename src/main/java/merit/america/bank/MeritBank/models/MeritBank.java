package merit.america.bank.MeritBank.models;

import java.util.Arrays;
import java.util.HashMap;

public class MeritBank {
	public static final double FRAUD_LIMIT = 1000;

	public static final double COMBINED_BALANCE_MAX = 250000;

	public static HashMap<Long, BankAccount> accounts = new HashMap<>();

	public static long accountNumber = 1;
	public static long accountHolderNumber = 1;
	public static long cdoNumber = 1;
	public static AccountHolder[] accountHolders = new AccountHolder[0];
	public static CDOffering[] cdOfferings = new CDOffering[0];
	public static CDOffering offering;


	public static double recursivePower(double base, int exponent) {
		if (exponent == 0)
			return 1;
		else
			return base * recursivePower(base, (exponent - 1));
	}


	public static double recursiveFutureValue(double amount, int years, double interestRate) {
		return amount * recursivePower((1 + interestRate), years);
	}

	public static BankAccount getBankAccount(long accountID) // i. Return null if account not found
	{
		if (accounts.containsKey(accountID))
			return accounts.get(accountID);

		return null;
	}

	static AccountHolder[] sortAccountHolders() {
		int l = 0;
		while (accountHolders[l] != null) {
			l++;
		}
		Arrays.sort(accountHolders, 0, l);

		return accountHolders;
	}

	static void setNextAccountNumber(long nextAccountNumber) {
		accountNumber = nextAccountNumber;
	}

	public static long getNextAccountNumber() {
		return accountNumber++;
	}
	
	public static long getNextAccountHolderNumber() {
		return accountHolderNumber++;
	}
	
	public static long getNextcdoNumber() {
		return cdoNumber++;
	}
	
	public static AccountHolder getAccountHolder(long id) {
		for (AccountHolder ach : accountHolders) {
			if (id == ach.getId())
				return ach;		
		}
		return null;
	}

	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	 public static void addAccountHolder(AccountHolder accountHolder) {
		 accountHolder.setId(getNextAccountHolderNumber());
	        AccountHolder[] tmp = new AccountHolder[accountHolders.length+1];
	        for (int i = 0; i<accountHolders.length; i++) {
	            tmp[i] = accountHolders[i];
	        }
	        tmp[accountHolders.length] = accountHolder;
	        accountHolders = tmp;
	    }

	public static AccountHolder[] getAccountHolders() {
		return accountHolders;
	}

	public static CDOffering[] getCDOfferings() {
		return cdOfferings;
	}

	public static CDOffering getBestCDOffering(double depositAmount) {
		if (cdOfferings != null) {
			double val = 0.0;
			int j = 0;
			for (int i = 0; i < cdOfferings.length; i++) {
				if ((double) recursiveFutureValue(depositAmount, cdOfferings[i].getTerm(),
						cdOfferings[i].getInterestRate()) > val) {
					val = (double) recursiveFutureValue(depositAmount, cdOfferings[i].getTerm(),
							cdOfferings[i].getInterestRate());
					j = i;
				}
			}
			return cdOfferings[j];
		} else
			return null;
	}

	public static CDOffering getSecondBestCDOffering(double depositAmount) {
		if (cdOfferings != null) {
			double val = 0.0;
			int j = 0;
			int k = 0;
			for (int i = 0; i < cdOfferings.length; i++) {
				if ((double) recursiveFutureValue(depositAmount, cdOfferings[i].getTerm(),
						cdOfferings[i].getInterestRate()) > val) {
					val = (double) recursiveFutureValue(depositAmount, cdOfferings[i].getTerm(),
							cdOfferings[i].getInterestRate());
					j = i;
					k = j;
				}
			}

			return cdOfferings[k];
		} else
			return null;
	}

	public static void clearCDOfferings() {
		cdOfferings = null;
	}

	public static void setCDOfferings(CDOffering[] offerings) {
		cdOfferings = offerings;
	}

	public static double totalBalances() {
		double total = 0.0;
		for (int i = 0; i < accountHolders.length; i++) {
			if (accountHolders[i] != null)
				total += accountHolders[i].getCombinedBalance();
			else
				break;
		}

		return total;
	}
	
	public static void addCDO(CDOffering cdOffering) {
		cdOffering.setId(getNextcdoNumber());
		CDOffering[] tmp = new CDOffering[cdOfferings.length+1];
		for(int i = 0; i < cdOfferings.length; i++) {
			tmp[i] = cdOfferings[i];
		}
		tmp[cdOfferings.length] = cdOffering;
		MeritBank.cdOfferings = tmp;
	}
	
	public static CDOffering getCDOffering(long id) {
		for (CDOffering ach : cdOfferings) {
			if (id == ach.getId())
				return ach;		
		}
		return null;
	}
}
