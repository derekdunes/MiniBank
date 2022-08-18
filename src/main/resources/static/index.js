const createForm = document.querySelectorAll('.create-form');
const depositForm = document.querySelectorAll('.deposit-form');
const withdrawForm = document.querySelectorAll('.withdraw-form');
const transactionForm = document.querySelectorAll('.transaction-form');

const createSubmitInput = document.querySelector('#create_submit')
const depositSubmitInput = document.querySelector('#deposit_submit')
const withdrawSubmitInput = document.querySelector('#withdraw_submit')
const transactionSubmitInput = document.querySelector('#transaction_submit')

// const fillDonationSection = document.querySelector('section[id="fill-donation"]');
// const confirmDonationSection = document.querySelector('section[id="confirm-donation"]');
// const cancelDonationSection = document.getElementById("cancel-section");
// const confirmedDonationSection = document.getElementById("confirm-section");
// const errorSection = document.getElementById("error-section");

document.addEventListener('DOMContentLoaded', function () {

    createSubmitInput.addEventListener('click', getCreateFormData, false);
    depositSubmitInput.addEventListener('click', getDepositFormData, false);
    withdrawSubmitInput.addEventListener('click', getWithdrawFormData, false);
    transactionSubmitInput.addEventListener('click', getTransactionFormData, false);

}, false);

function getCreateFormData(e) {

    e.preventDefault();

    setError('create_error','', false);

    console.log("Create form" + createForm)

    //get the name and phone number and reset the fields
    var formData = new FormData(document.forms[0]);

    //validate the fields
    if(!isCreateFormValid(formData)){
        return;
    }

    var name = formData.get('name')
    var phone = formData.get('phone_number')

    //create the json object and send the request to the back end
    var req = {
        "name": name,
        "phoneNumber": phone
    }

    for (let index = 0; index < 10; index++) {
        //get the response check the response code
        console.log("loop of count " + index);
        sendRequest(req, '/create')   
    }

}

function getDepositFormData(e) {

    //get the name and phone number and reset the fields
    e.preventDefault();

    setError('deposit_error','', false);

    //get the name and phone number and reset the fields
    var formData = new FormData(document.forms[1]);

    //validate the fields
    if(!isFormValid(formData, 'deposit_error', false)){
        return;
    }

    var account = formData.get('account')
    var amount = formData.get('amount')

    //create the json object and send the request to the back end
    for (let index = 0; index < 20; index++) {
        const newAmount = Math.floor(Math.random() * 1000);
        req = {
            "accountNumber": account,
            "amount": newAmount
        }

        //get the response check the response code
        sendRequest(req, '/deposit')
    }
    
}

function getWithdrawFormData(e) {
    //get the name and phone number and reset the fields
    e.preventDefault();

    setError('withdraw_error','', false);

    //get the name and phone number and reset the fields
    var formData = new FormData(document.forms[2]);

    //validate the fields
    if(!isFormValid(formData, 'withdraw_error', false)){
        return;
    }

    var account = formData.get('account')
    var amount = formData.get('amount')

    //create the json object and send the request to the back end

    for (let index = 0; index < 25; index++) {
        const newAmount = Math.floor(Math.random() * 10000);
        req = {
            "accountNumber": account,
            "amount": newAmount
        }

        //get the response check the response code
        sendRequest(req, '/withdraw')
    }

}

function getTransactionFormData(e) {
    //get the name and phone number and reset the fields
    e.preventDefault();

    setError('transaction_error','', false);

    //get the name and phone number and reset the fields
    var formData = new FormData(document.forms[3]);

    //validate the fields
    if(!isFormValid(formData, 'transaction_error', true)){
        return;
    }

    var account = formData.get('account')

    //create the json object and send the request to the back end
    var req = {
        "accountNumber": account,
    }

    //get the response check the response code
    sendRequest(req, '/transactions')

}

function sendRequest(req, endpoint) {
    //Get and save the content
    //fetch('');

    console.log(req);

    let headers = new Headers();

    headers.append('Content-Type', 'application/json');
    headers.append('Accept', '*/*');
    headers.append('Accept-Encoding', 'gzip, deflate, br');
    headers.append('Host', 'http://localhost:4000');
    headers.append('connection', 'keep-alive')

    //save to db
    fetch('http://localhost:4000/account' + endpoint, {
        headers: headers,
        method: 'POST',
        body: JSON.stringify(req)
    })
    .then(response => {
        if (!response.ok) {
            //show the error page
            // confirmDonationSection.hidden=true;
            // confirmDonationSection.innerHTML="";
            // errorSection.hidden=false;
            throw new Error("HTTP error" + response.stats);

        }

        return response.json();
    })
    .then(data => {
        console.log(data)
        //modify UI depending on where the request came from
        if(endpoint === '/create'){
            createAccountResponse(data);
        } else if(endpoint === '/deposit'){
            withDepResponse(data, 'deposit');
        } else if(endpoint === '/withdraw'){
            withDepResponse(data, 'withdraw');
        } else {
            //transactions response
            transactionResponse(data);
        }
    })
    .catch(err => {
        //if there is an error show the error page
        // confirmDonationSection.hidden=true;
        // confirmDonationSection.innerHTML="";
        // errorSection.hidden=false;
        console.log(err);
    });


}

function createAccountResponse(data) {
    // <div class="badge bg-primary text-wrap" style="width: 6rem;">
    //     2167239232
    // </div>

    //check response code and show error according
    if(data.responseCode === '1'){
        //show error
        setError('create_error', data.responseMessage, true)
        return;
    }

    const accountResultSection = document.getElementById("account_results");

    var divElement = document.createElement('div')

    divElement.className = 'badge bg-primary text-wrap'
    divElement.style = 'width: 6rem;'
    divElement.innerText = data.additionalInfo[0].paramValue

    accountResultSection.append(divElement);
    accountResultSection.append(document.createElement('div'))

}

function withDepResponse(data, type) {
    // <h6>Wallet Balance <span class="badge bg-secondary">N5000</span></h6>
    // <h6>Withdraw Amount <span class="badge bg-danger">N1000</span></h6>
    //<h6>Deposit Amount <span class="badge bg-success">N1000</span></h6>

    //check response code and show error according

    const resultSection = type === 'deposit' ? document.getElementById("dep_res") : document.getElementById("with_res");

    if(data.responseCode === '1'){
        //show error
        if(type === 'deposit'){
            setError('deposit_error', data.responseMessage, true)
        }

        if(type === 'withdraw'){
            setError('withdraw_error', data.responseMessage, true)
        }

        //resultSection.innerHTML = '';

        return;
    }


    const walletHeader = document.createElement('h6')
    const walletSpan = document.createElement('span');

    walletSpan.className = 'badge bg-secondary'
    walletSpan.innerText = data.additionalInfo[1].paramValue;

    walletHeader.innerText = 'Wallet Balance '
    walletHeader.append(walletSpan);

    const amountHeader = document.createElement('h6')
    const amountSpan = document.createElement('span');

    var amountSpanClass = type === 'deposit' ? 'badge bg-success' : 'badge bg-danger';

    amountSpan.className = amountSpanClass;
    amountSpan.innerText = data.additionalInfo[0].paramValue;

    amountHeader.innerText = type === 'deposit' ? 'Deposit Amount ' : 'Withdraw Amount ';
    amountHeader.append(amountSpan);


    //resultSection.innerHTML = '';
    resultSection.append(document.createElement('br'))
    resultSection.append(walletHeader);
    //resultSection.append(document.createElement('div'))
    resultSection.append(amountHeader);

}

function transactionResponse(data) {
    // <tbody id="transaction_table">
    // <tr>
    //   <th scope="row">1</th>
    //   <td>203489334</td>
    //   <td>DEPOSIT</td>
    //   <td>DEPOSIT</td>
    //   <td>1000.0</td>
    //   <td>2012/23/03</td>
    // </tr>

    const tableResultSection = document.getElementById("transaction_table");
    tableResultSection.innerHTML = '';//clear table

    //check response code and show error according
    if(data.responseCode === '1'){
        //show error
        setError('deposit_error', data.responseMessage, true)

        //reset table results
        return;
    }

    var transactionArray = data.transactions;

    transactionArray.forEach(element => {

        var tableRow = document.createElement('tr');

        var tableRowHead = document.createElement('th');
        tableRowHead.innerHTML = element.id;

        var accountNumber = document.createElement('td');
        accountNumber.innerHTML = element.toAccount;

        var transactionType = document.createElement('td');
        transactionType.innerHTML = element.type;

        var description = document.createElement('td');
        description.innerHTML = element.description;

        var amount = document.createElement('td');
        amount.innerHTML = element.amount;

        var transactionDate = document.createElement('td');
        transactionDate.innerHTML = element.createdAt;

        tableRow.append(tableRowHead);
        tableRow.append(accountNumber);
        tableRow.append(transactionType);
        tableRow.append(description);
        tableRow.append(amount);
        tableRow.append(transactionDate);

        tableResultSection.append(tableRow);
    });

}

function isEmpty(field){
    return field === '';
}

function setError(id, message, show){

    const errorDiv = document.getElementById(id);

    if(show === true){
        errorDiv.hidden = false;
        errorDiv.innerText = message;
    } else {
        errorDiv.hidden = true;
    }

}

function isFormValid(formData, errorId, isTransaction){
    //make sure both numbers is only only number
    const numberOnlyRegex = new RegExp('^[0-9]+$');

    var account = formData.get('account').trim();

    if(isTransaction === false) {

    //check if they are empty first
    if(isEmpty(account)){
        setError(errorId, "Account Number field is empty, please provide a valid number", true)
        return false;
    }

    if(!numberOnlyRegex.test(account)){
        setError(errorId, "Account Number field contains invalid characters, please provide a valid number", true)
        return false;
    }

    if(account.length != 10){
        setError(errorId, "Account number field lenght is invalid, please provide a valid number lenght(10)", true)
        return false;
    }

        var amount = formData.get('amount').trim();

        if(isEmpty(amount)){
            setError(errorId, "Amount field is empty, please provide a valid number", true)
            return false;
        }

        if(!numberOnlyRegex.test(amount)){
            setError(errorId, "Amount field contains invalid characters, please provide a valid number", true)
            return false;
        }

    }


    return true;
}

function isCreateFormValid(formData){

    var name = formData.get('name').trim();
    var phone = formData.get('phone_number').trim();

    //check if they are empty first
    if(isEmpty(name)){
        setError('create_error', "Your name field is empty please provide a valid name", true)
        return false;
    }

    if(isEmpty(phone)){
        setError('create_error', "Phone number field is empty please provide a valid number", true)
        return false;
    }

    //make sure name is only letters
    const alphabetRegex = new RegExp('^[a-zA-Z\\s]+$');

    if(!alphabetRegex.test(name)){
        setError('create_error', "Name field contains invalid characters, please provide a valid name", true)
        return false;
    }

    //make sure phone number is only 11 digits and contains only number
    const numberOnlyRegex = new RegExp('^[0-9]+$');

    if(!numberOnlyRegex.test(phone)){
        setError('create_error', "phone number field contains invalid characters, please provide a valid number", true)
        return false;
    }

    if(phone.length != 11){
        setError('create_error', "phone number field lenght is invalid, please provide a valid number", true)
        return false;
    }

    return true;
}