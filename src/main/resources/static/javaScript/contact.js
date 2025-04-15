console.log("contact.js loaded");
const baseURL = "http://localhost:8080";
const viewContactModal = document.getElementById('view_contact_modal');

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'view_contact_modal',
  override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal() {
    contactModal.show();
}

function closeContactModal() {
    contactModal.hide();
}

async function loadContactData(id){
    // this function will load data in modal from api/contacts
    console.log(id);
try{
    const data = await( await fetch(`${baseURL}/api/contacts/${id}`)).json();
    document.getElementById('contact_name').innerText = data.name;
    document.getElementById('contact_email').innerText = data.email;
    document.getElementById('contact_phone').innerText = data.phoneNumber;
    document.getElementById('contact_address').innerText = data.address;
    document.getElementById('contact_about').innerText = data.description;
     document.getElementById('contact_profile').src = data.picture;
    // document.getElementById('contact_website').innerText = data.websiteLink;
    // document.getElementById('contact_linkedin').innerText = data.linkedIn;
    if (data.favourite) {
        contactFavorite.innerHTML =
          "<i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i>";
    } else {
        contactFavorite.innerHTML = "Not Favorite Contact";
    }
    //this is another way to send data to html
    document.querySelector("#contact_website").href = data.websiteLink;
    document.querySelector("#contact_website").innerHTML = data.websiteLink;
    document.querySelector("#contact_linkedIn").href = data.linkedIn;
    document.querySelector("#contact_linkedIn").innerHTML = data.linkedIn;

    console.log(data);
} catch (error){
    console.log(error);
}
    
console.log("fetch completed");
    openContactModal();

}

//delete contact by id
async function deleteContactByID(contactId){
    Swal.fire({
        title: "Do you want to delete the contact?",
        icon : "warning",
        showCancelButton: true,
        confirmButtonText: "Delete",
        customClass: {
            confirmButton: "bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded mr-3",
            cancelButton: "space-x-3 bg-gray-500 hover:bg-gray-600 text-white font-bold py-2 px-4 rounded"
        },
        buttonsStyling: false, // Disable SweetAlert2's default button styles
      }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            const url = `${baseURL}/user/contacts/delete/`+contactId;
            window.location.replace(url);
          Swal.fire("Deleted!", "", "success");
        } 
    });
}