



const menuToggle = document.getElementById('menuToggle');
const sidebar = document.getElementById('sidebar');
const mainContent = document.getElementById('mainContent');
const logoutBtn = document.getElementById('logout');
const sessionLoading = document.getElementById('sessionLoading');
const sessionTable = document.getElementById('sessionTable');

const userId = sessionStorage.getItem("userId");


function showModule(moduleId) {
const allModules = document.querySelectorAll('.module');
allModules.forEach(mod => mod.style.display = 'none'); // hide all

const selectedModule = document.getElementById(moduleId);
if (selectedModule) {
selectedModule.style.display = 'block'; // show the selected one
}

// Optional logic for individual modules
if (moduleId === 'manageUsersModule') {
loadUsers(); // Function to load users dynamically
}

if (moduleId === 'reportsModule') {
loadRecent20Users();
}

if (moduleId === 'adminProfileModule') {
loadUserProfile();
}

if (moduleId === 'settingsModule') {
initializeSettingsModule(); // ⬅️ This will run only when you open settings
}

if (moduleId === 'chatModule') {
loadChatMessages();
}
}





// Toggle sidebar
menuToggle.addEventListener('click', () => {
    sidebar.classList.toggle('active');
    mainContent.classList.toggle('expanded');
});

function formatDateTime(dateTimeStr) {
    if (!dateTimeStr) return 'N/A';
    const date = new Date(dateTimeStr);
    return new Intl.DateTimeFormat('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    }).format(date);
}

function loadAdminDashboardData() {
    if (!userId) {
        alert("Admin ID not found in session. Please login again.");
        window.location.href = "adminSignin.html";
        return;
    }

    sessionLoading.style.display = 'flex';
    sessionTable.style.display = 'none';

    fetch(`http://localhost:8080/api/admin/${userId}`)
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch admin session');
            return response.json();
        })
        .then(data => {
            sessionLoading.style.display = 'none';
            sessionTable.style.display = 'table';

            document.getElementById("userIdDisplay").textContent = data.userId || "N/A";
            document.getElementById("userNameDisplay").textContent = data.userName || "N/A";
            document.getElementById("signinTimeDisplay").textContent = formatDateTime(data.signinTime);
            document.getElementById("logoutTimeDisplay").textContent = data.logoutTime
                ? formatDateTime(data.logoutTime)
                : "Active";
            document.getElementById("roleDisplay").textContent = data.role || "ADMIN";

            const statusDisplay = document.getElementById("statusDisplay");
            const sidebarStatus = document.getElementById("adminStatus");

            if (data.isActive) {
                statusDisplay.innerHTML = '<span style="color: green;">Online</span>';
                sidebarStatus.textContent = "Online";
                sidebarStatus.style.color = "green";
            } else {
                statusDisplay.innerHTML = '<span style="color: red;">Offline</span>';
                sidebarStatus.textContent = "Offline";
                sidebarStatus.style.color = "red";
            }

            document.getElementById("sidebarAdminName").textContent = data.userName || "Unknown";
            document.getElementById("headerAdminName").textContent = data.userName || "Unknown";
            document.getElementById("sidebarAdminRole").textContent = data.role || "Admin";
            document.getElementById("headerAdminRole").textContent = `(${data.role || "Admin"})`;

            const avatar = data.userName ? data.userName.charAt(0).toUpperCase() : "?";
            document.getElementById("adminAvatar").textContent = avatar;

            sessionStorage.setItem("role", data.role);
        })
        .catch(error => {
            console.error("Error loading admin dashboard:", error);
            sessionLoading.style.display = 'none';
            alert("Failed to load dashboard data. Please login again.");
            window.location.href = "adminSignin.html";
        });
}

logoutBtn.addEventListener('click', function () {
    if (!userId) {
        alert("No admin ID found. Already logged out.");
        window.location.href = "adminSignin.html";
        return;
    }

    fetch('http://localhost:8080/api/admin/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ userId: userId })
    })
        .then(response => {
            if (!response.ok) throw new Error('Logout failed');
            sessionStorage.clear();
            window.location.href = "adminSignin.html";
        })
        .catch(error => {
            console.error('Logout error:', error);
            alert('Logout failed. Try again.');
        });
});

document.addEventListener('DOMContentLoaded', function () {
    loadAdminDashboardData();
    setInterval(loadAdminDashboardData, 30000); // Refresh every 30 seconds
});

async function fetchAdminNotifications() {
try {
const response = await fetch("http://localhost:8080/admin-notifications/unread");
const notifications = await response.json();

const notificationList = document.getElementById("adminNotificationList");
const notificationCount = document.getElementById("notificationCount");

notificationList.innerHTML = "";
notificationCount.textContent = notifications.length;

notifications.forEach(notification => {
    const item = document.createElement("li");
    item.innerText = notification.message;
    item.onclick = async () => {
        await markAsRead(notification.id);
        fetchAdminNotifications();
    };
    notificationList.appendChild(item);
});

} catch (error) {
console.error("Error fetching notifications:", error);
}
}

// ✅ Mark notification as read in the database
async function markAsRead(notificationId) {
try {
await fetch(`http://localhost:8080/admin-notifications/mark-as-read/${notificationId}`, {
    method: "POST",
    headers: { "Content-Type": "application/json" }
});
} catch (error) {
console.error("Error marking notification as read:", error);
}
}

// Close Notification Panel
function closeNotifications() {
document.getElementById("notificationPanel").style.display = "none";
}

// Toggle Notifications Panel
function toggleNotifications() {
const panel = document.getElementById("notificationPanel");
panel.style.display = panel.style.display === "block" ? "none" : "block";
}

// Load Initial Data

fetchAdminNotifications();








let allUsers = [];

async function loadUsers() {
const loadingDiv = document.getElementById("userLoading");
const table = document.getElementById("userTable");
const tbody = document.getElementById("userTableBody");

loadingDiv.style.display = "block";
table.style.display = "none";

try {
const response = await fetch("http://localhost:8080/api/all-users");
if (!response.ok) throw new Error("Failed to fetch users");

allUsers = await response.json();
displayAllUsers(allUsers);
} catch (error) {
console.error("Error loading users:", error);
loadingDiv.innerText = "Error loading user data.";
}
}

function displayAllUsers(users) {
const tbody = document.getElementById("userTableBody");
const loadingDiv = document.getElementById("userLoading");
const table = document.getElementById("userTable");

tbody.innerHTML = "";

if (users.length === 0) {
tbody.innerHTML = `<tr><td colspan="5">No matching users found.</td></tr>`;
} else {
users.forEach(user => {
const row = document.createElement("tr");
row.innerHTML = `
  <td>${user.userId}</td>
  <td>${user.userName}</td>
  <td>${user.email}</td>
  <td>${user.phone}</td>
  <td>${user.role}</td>
`;
tbody.appendChild(row);
});
}

loadingDiv.style.display = "none";
table.style.display = "table";
}

function filterAndSortAllUsers() {
const query = document.getElementById("allUserSearchInput").value.toLowerCase();
const sortBy = document.getElementById("allUserSortSelect").value;

let filteredUsers = allUsers.filter(user => {
return (
user.userId.toLowerCase().includes(query) ||
user.userName.toLowerCase().includes(query) ||
user.role.toLowerCase().includes(query)
);
});

if (sortBy && filteredUsers.length > 0) {
filteredUsers.sort((a, b) => {
const aVal = (a[sortBy] || "").toLowerCase();
const bVal = (b[sortBy] || "").toLowerCase();
return aVal.localeCompare(bVal);
});
}

displayAllUsers(filteredUsers);
}

document.addEventListener("DOMContentLoaded", () => {
loadUsers();
document.getElementById("allUserSearchInput").addEventListener("input", filterAndSortAllUsers);
document.getElementById("allUserSortSelect").addEventListener("change", filterAndSortAllUsers);
});














let recentUsers = [];

async function loadRecent20Users() {
const loadingDiv = document.getElementById("recentUsersLoading");
const table = document.getElementById("recentUsersTable");
const tbody = document.getElementById("recentUsersTableBody");

loadingDiv.style.display = "block";
table.style.display = "none";

try {
const response = await fetch("http://localhost:8080/api/users/recent");
if (!response.ok) throw new Error("Failed to fetch users");

recentUsers = await response.json(); // make sure this gets filled
displayUsers(recentUsers);
} catch (error) {
console.error("Error loading users:", error);
loadingDiv.innerText = "Error loading user data.";
}
}

function displayUsers(users) {
const tbody = document.getElementById("recentUsersTableBody");
const loadingDiv = document.getElementById("recentUsersLoading");
const table = document.getElementById("recentUsersTable");

tbody.innerHTML = "";

if (users.length === 0) {
tbody.innerHTML = `<tr><td colspan="4">No matching users found.</td></tr>`;
} else {
users.forEach(user => {
const row = document.createElement("tr");
row.innerHTML = `
  <td>${user.userId}</td>
  <td>${user.userName}</td>
  <td>${user.role}</td>
  <td>${formatDate(user.createdAt)}</td>
`;
tbody.appendChild(row);
});
}

loadingDiv.style.display = "none";
table.style.display = "table";
}

function formatDate(dateString) {
const date = new Date(dateString);
return date.toLocaleString();
}


function filterAndSortUsers() {
const query = document.getElementById("searchInput").value.toLowerCase();
const sortBy = document.getElementById("sortSelect").value;

let filteredUsers = recentUsers.filter(user => {
return (
user.userId.toLowerCase().includes(query) ||
user.userName.toLowerCase().includes(query) ||
user.role.toLowerCase().includes(query)
);
});

if (sortBy && filteredUsers.length > 0) {
filteredUsers.sort((a, b) => {
const aVal = (a[sortBy] || "").toLowerCase();
const bVal = (b[sortBy] || "").toLowerCase();
return aVal.localeCompare(bVal);
});
}

displayUsers(filteredUsers);
}

document.addEventListener("DOMContentLoaded", () => {
loadRecent20Users();
document.getElementById("searchInput").addEventListener("input", filterAndSortUsers);
document.getElementById("sortSelect").addEventListener("change", filterAndSortUsers);
});












async function downloadUserReport() {
const { jsPDF } = window.jspdf;
const doc = new jsPDF();

const title = "User Management Report";
const date = new Date().toLocaleString();

// Add optional logo (uncomment and use base64 image if needed)
// doc.addImage("data:image/png;base64,...", "PNG", 10, 10, 30, 30);

// Add Title
doc.setFontSize(18);
doc.text(title, 14, 20);

// Add Date
doc.setFontSize(10);
doc.setTextColor(100);
doc.text("Generated on: " + date, 14, 27);

// Grab the user table
const table = document.getElementById("userTable");
const headers = Array.from(table.querySelectorAll("thead th")).map(th => th.innerText);
const rows = Array.from(table.querySelectorAll("tbody tr")).map(tr =>
Array.from(tr.querySelectorAll("td")).map(td => td.innerText)
);

// Create the PDF table
doc.autoTable({
head: [headers],
body: rows,
startY: 35,
theme: "grid",
styles: {
fontSize: 10,
cellPadding: 3,
},
headStyles: {
fillColor: [60, 141, 188],
textColor: 255,
fontStyle: 'bold',
},
alternateRowStyles: {
fillColor: [245, 245, 245]
}
});

// Download file
const fileName = "User_Report_" + new Date().toISOString().slice(0, 10) + ".pdf";
doc.save(fileName);
}






async function downloadAllUsersReport() {
const { jsPDF } = window.jspdf;
const doc = new jsPDF();

const title = "All Users Report";
const date = new Date().toLocaleString();

// Optional: Add your logo (base64 format image)
// doc.addImage("data:image/png;base64,...", "PNG", 10, 10, 30, 30);

// Title
doc.setFontSize(18);
doc.text(title, 14, 20);

// Date
doc.setFontSize(10);
doc.setTextColor(100);
doc.text("Generated on: " + date, 14, 27);

// Fetch latest all users data
try {
const response = await fetch("http://localhost:8080/api/all-users");
if (!response.ok) throw new Error("Failed to fetch users");
const users = await response.json();

if (!users.length) {
alert("No users found to generate the report.");
return;
}

// Format headers and rows
const headers = [["User ID", "User Name", "Email", "Phone", "Role"]];
const rows = users.map(user => [
user.userId,
user.userName,
user.email,
user.phone,
user.role
]);

// Create the table in the PDF
doc.autoTable({
head: headers,
body: rows,
startY: 35,
theme: "grid",
styles: {
fontSize: 10,
cellPadding: 3,
},
headStyles: {
fillColor: [60, 141, 188],
textColor: 255,
fontStyle: 'bold',
},
alternateRowStyles: {
fillColor: [245, 245, 245]
}
});

// Save PDF
const fileName = "All_Users_Report_" + new Date().toISOString().slice(0, 10) + ".pdf";
doc.save(fileName);
} catch (error) {
console.error("Error generating PDF:", error);
alert("Failed to generate report.");
}
}





function loadUserProfile() {
const userId = sessionStorage.getItem("userId");

if (!userId) {
console.error("No userId found in sessionStorage");
return;
}

fetch(`http://localhost:8080/api/profile/${userId}`)
.then(response => {
if (!response.ok) {
throw new Error("Failed to fetch user profile");
}
return response.json();
})
.then(data => {
document.getElementById("profileUserId").textContent = data.userId || "N/A";
document.getElementById("profileUserName").textContent = data.userName || "N/A";
document.getElementById("profileEmail").textContent = data.email || "N/A";
document.getElementById("profilePhone").textContent = data.phone || "N/A";
document.getElementById("profileRole").textContent = data.role || "N/A";
document.getElementById("profileReferralCode").textContent = data.referralCode || "N/A";
document.getElementById("profileCreatedAt").textContent = data.createdAt || "N/A";


const pic = localStorage.getItem("adminProfileImage");
  if (pic) {
    document.getElementById("profileImage").src = pic;
    document.getElementById("settingsImage").src = pic;
    document.getElementById("profileImage").classList.add("visible");
    document.getElementById("settingsImage").classList.add("visible");
  }


})
.catch(error => {
console.error("Error loading profile:", error);
});
}









// Load data on page load
function initializeSettingsModule() {
    const userId = sessionStorage.getItem("userId");

    if (!userId) {
        console.error("No userId found in sessionStorage");
        showStatus("No user session found", true);
        return;
    }

    fetch(`http://localhost:8080/api/profile/${userId}`)
        .then(res => res.json())
        .then(data => {
            document.getElementById("currentUsername").textContent = data.userName || "N/A";
            document.getElementById("currentPhone").textContent = data.phone || "N/A";
        })
        .catch(() => showStatus("Failed to load profile", true));
}



 // Edit profile (triggers both username and phone edit)
 function editProfile() {
    editUsername();
    editPhone();
    document.getElementById("editProfileBtn").style.display = "none";
}

// Edit username
function editUsername() {
    document.getElementById("usernameEdit").classList.add("active");
    document.getElementById("usernameInput").value = document.getElementById("currentUsername").textContent;
    document.getElementById("currentUsername").style.display = "none";
}

// Edit phone
function editPhone() {
    document.getElementById("phoneEdit").classList.add("active");
    document.getElementById("phoneInput").value = document.getElementById("currentPhone").textContent;
    document.getElementById("currentPhone").style.display = "none";
}

// Cancel edit
function cancelEdit(field) {
    if (field === 'username') {
        document.getElementById("usernameEdit").classList.remove("active");
        document.getElementById("currentUsername").style.display = "inline-block";
    } else if (field === 'phone') {
        document.getElementById("phoneEdit").classList.remove("active");
        document.getElementById("currentPhone").style.display = "inline-block";
    }

    // Show Edit button only if both edit sections are inactive
    if (!document.getElementById("usernameEdit").classList.contains("active") &&
        !document.getElementById("phoneEdit").classList.contains("active")) {
        document.getElementById("editProfileBtn").style.display = "inline-block";
    }
}

// Save username with validation
function saveUsername() {
    const updatedName = document.getElementById("usernameInput").value.trim();
    const userId = sessionStorage.getItem("userId");
    const usernameRegex = /^(?!\s)(?!.*\s{2,})[a-zA-Z0-9 ]+(?<!\s)$/;

    if (!updatedName) {
        showStatus("Username cannot be empty", true);
        return;
    }
    if (!usernameRegex.test(updatedName)) {
        showStatus("Username must contain only letters, numbers, and single spaces, and not start/end with space", true);
        return;
    }

    fetch("http://localhost:8080/api/admin/update-username", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userId, userName: updatedName }),
    })
        .then(res => {
            if (!res.ok) throw new Error("Failed to update");
            return res.json();
        })
        .then(data => {
            document.getElementById("currentUsername").textContent = data.userName;
            document.getElementById("usernameEdit").classList.remove("active");
            document.getElementById("currentUsername").style.display = "inline-block";
            showStatus("Username updated successfully.");

            // Show Edit button if both edit sections are inactive
            if (!document.getElementById("phoneEdit").classList.contains("active")) {
                document.getElementById("editProfileBtn").style.display = "inline-block";
            }
        })
        .catch(() => showStatus("Failed to update username", true));
}

// Save phone with validation
function savePhone() {
    const updatedPhone = document.getElementById("phoneInput").value.trim();
    const userId = sessionStorage.getItem("userId");
    const phoneRegex = /^[6-9]\d{9}$/;

    if (!updatedPhone) {
        showStatus("Phone number cannot be empty", true);
        return;
    }
    if (!phoneRegex.test(updatedPhone)) {
        showStatus("Phone number must start with 6-9 and be 10 digits", true);
        return;
    }

    fetch("http://localhost:8080/api/admin/update-phone", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userId, phone: updatedPhone }),
    })
        .then(res => {
            if (!res.ok) throw new Error("Failed to update");
            return res.json();
        })
        .then(data => {
            document.getElementById("currentPhone").textContent = data.phone;
            document.getElementById("phoneEdit").classList.remove("active");
            document.getElementById("currentPhone").style.display = "inline-block";
            showStatus("Phone number updated successfully.");

            // Show Edit button if both edit sections are inactive
            if (!document.getElementById("usernameEdit").classList.contains("active")) {
                document.getElementById("editProfileBtn").style.display = "inline-block";
            }
        })
        .catch(() => showStatus("Failed to update phone number", true));
}













// Show status
function showStatus(message, isError = false) {
    const el = document.getElementById("settingsStatus");
    el.textContent = message;
    el.style.color = isError ? "red" : "green";
    el.classList.add("visible");
    setTimeout(() => {
        el.classList.remove("visible");
        el.textContent = "";
    }, 3000);
}


// Show success popup
function showSuccessPopup(message) {
    const popup = document.getElementById("successPopup");
    const messageEl = document.getElementById("successMessage");
    messageEl.textContent = message;
    popup.style.display = "block";
    setTimeout(() => {
        closePopup();
    }, 3000); // Auto-close after 3 seconds
}

// Close popup
function closePopup() {
    const popup = document.getElementById("successPopup");
    popup.style.display = "none";
}

// Show status for failures
function showStatus(message, isError = false) {
    const el = document.getElementById("settingsStatus");
    el.textContent = message;
    el.style.color = isError ? "red" : "green";
    el.classList.add("visible");
    setTimeout(() => {
        el.classList.remove("visible");
        el.textContent = "";
    }, 3000);
}




const profilePic = document.getElementById("profilePic");
const settingsPic = document.getElementById("settingsPic");
const uploadInput = document.getElementById("uploadPicInput");


const pic = localStorage.getItem("adminProfileImage");
  if (pic) {
    document.getElementById("profileImage").src = pic;
    document.getElementById("settingsImage").src = pic;
    document.getElementById("profileImage").classList.add("visible");
    document.getElementById("settingsImage").classList.add("visible");
  }

// Profile picture
document.getElementById("uploadInput").addEventListener("change", function (e) {
const file = e.target.files[0];
if (file && file.type.startsWith("image/")) {
const reader = new FileReader();
reader.onload = function (event) {
  const base64Image = event.target.result;
  localStorage.setItem("adminProfileImage", base64Image);
  document.getElementById("profileImage").src = base64Image;
  document.getElementById("settingsImage").src = base64Image;
  document.getElementById("profileImage").classList.add("visible");
  document.getElementById("settingsImage").classList.add("visible");
  showStatus("Profile picture updated successfully.");
};
reader.readAsDataURL(file);
}
});


// Run on page load
initializeSettingsModule();
















let currentAdminId = 'ADMIN_0001'; // Replace with actual admin ID
let isAdmin = true; // true for admin
let receiverId = 'USER_0001'; // Replace with the logged-in user's ID

// Fetch chat messages from backend
function loadChatMessages() {
fetch(`http://localhost:8080/chat/conversation?userId=${receiverId}&adminId=${currentAdminId}`)
.then(res => res.json())
.then(data => {
const chatBox = document.getElementById('chatBox');
chatBox.innerHTML = ''; // Clear previous messages

data.forEach(msg => {
const div = document.createElement('div');
div.classList.add('chat-message');

if (msg.senderId === currentAdminId) {
  div.classList.add('admin-message');
} else {
  div.classList.add('user-message');
}

// Format timestamp to HH:MM AM/PM format
const timestamp = new Date(msg.timestamp);
const timeString = timestamp.toLocaleTimeString('en-US', {
  hour: '2-digit',
  minute: '2-digit',
  hour12: true,
  timeZone: 'Asia/Kolkata' // Change to your preferred time zone
});

// Show message and time
div.innerHTML = `<span>${msg.message}</span><br><small class="timestamp">${timeString}</small>`;
chatBox.appendChild(div);
});

chatBox.scrollTop = chatBox.scrollHeight; // Scroll to bottom
})
.catch(err => {
console.error('Error loading chat messages:', err);
});
}

// Send chat message
function sendChatMessage() {
const input = document.getElementById('chatInput');
const message = input.value.trim();
if (!message) return;

const payload = {
senderId: currentAdminId,
receiverId: receiverId,
message: message,
timestamp: new Date().toISOString()
};

fetch('http://localhost:8080/chat/send', {
method: 'POST',
headers: { 'Content-Type': 'application/json' },
body: JSON.stringify(payload)
})
.then(res => {
if (res.ok) {
input.value = ''; // Clear input field
loadChatMessages(); // Refresh chat
}
})
.catch(err => {
console.error('Error sending message:', err);
});
}

// Auto-refresh chat every 5 seconds
setInterval(loadChatMessages, 100000);
window.onload = loadChatMessages;


