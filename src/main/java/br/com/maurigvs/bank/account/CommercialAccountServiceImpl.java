package br.com.maurigvs.bank.account;

import br.com.maurigvs.bank.accountholder.Person;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
class CommercialAccountServiceImpl extends AccountServiceImpl implements CommercialAccountService {

    public CommercialAccountServiceImpl(AccountRepository repository) {
        super(repository);
    }

    @Override
    public void addAuthorizedUser(Long accountNumber, Person person) {
        CommercialAccount account = getCommercialAccountByNumber(accountNumber);
        account.getAuthorizedUsers().add(person);
    }

    @Override
    public boolean isAuthorizedUser(Long accountNumber, Person person) {
        CommercialAccount account = (CommercialAccount) getAccountByNumber(accountNumber);
        return account.getAuthorizedUsers().contains(person);
    }

    CommercialAccount getCommercialAccountByNumber(Long accountNumber){
        Account account = getAccountByNumber(accountNumber);
        if(!(account instanceof CommercialAccount))
            throw new EntityNotFoundException("The account is not a commercial account");
        return (CommercialAccount) account;
    }
}