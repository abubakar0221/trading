
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
        

    if (moduleId === 'userProfileModule') {
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
  


        // Format datetime for display
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
    
        // Load dashboard data
        function loadDashboardData() {
            if (!userId) {
                alert("User ID not found in session. Please login again.");
                window.location.href = "signin.html";
                return;
            }
    
            sessionLoading.style.display = 'flex';
            sessionTable.style.display = 'none';
    
            fetch(`http://localhost:8080/api/user_sessions/${userId}`)
                .then(response => {
                    if (!response.ok) throw new Error('Failed to fetch session');
                    return response.json();
                })
                .then(data => {
                    sessionLoading.style.display = 'none';
                    sessionTable.style.display = 'table';
    
                    // Session data
                    
                    document.getElementById("userIdDisplay").textContent = data.userId || "N/A";
                    document.getElementById("userNameDisplay").textContent = data.userName || "N/A";
                    document.getElementById("signinTimeDisplay").textContent = formatDateTime(data.signinTime);
                    document.getElementById("logoutTimeDisplay").textContent = data.logoutTime
                        ? formatDateTime(data.logoutTime)
                        : "Active";
                        document.getElementById("roleDisplay").textContent = data.role || "USER";
    
                    // Status
                    const statusDisplay = document.getElementById("statusDisplay");
                    const sidebarUserStatus = document.getElementById("userStatus");
    
                    if (data.isActive) {
                        statusDisplay.innerHTML = '<span style="color: green;">Online</span>';
                        sidebarUserStatus.textContent = "Online";
                        sidebarUserStatus.style.color = "green";
                    } else {
                        statusDisplay.innerHTML = '<span style="color: red;">Offline</span>';
                        sidebarUserStatus.textContent = "Offline";
                        sidebarUserStatus.style.color = "red";
                    }
    
                    // User Info
                    document.getElementById("sidebarUserName").textContent = data.userName || "Unknown";
                    document.getElementById("headerUserName").textContent = data.userName || "Unknown";
                    document.getElementById("sidebarUserRole").textContent = data.role || "User";
                    document.getElementById("headerUserRole").textContent = `(${data.role || "User"})`;
    
                    const avatar = data.userName ? data.userName.charAt(0).toUpperCase() : "?";
                    document.getElementById("userAvatar").textContent = avatar;
    
                    sessionStorage.setItem("role", data.role);
                })
                .catch(error => {
                    console.error("Error loading dashboard data:", error);
                    sessionLoading.style.display = 'none';
                    alert("Failed to load dashboard data. Please login again.");
                    window.location.href = "signin.html";
                });
        }
    
        // Handle logout
        logoutBtn.addEventListener('click', function () {
            if (!userId) {
                alert("No user ID found. Already logged out.");
                window.location.href = "signin.html";
                return;
            }
    
            fetch('http://localhost:8080/api/logout', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ userId: userId })
            })
                .then(response => {
                    if (!response.ok) throw new Error('Logout failed');
                    sessionStorage.clear();
                    window.location.href = "signin.html";
                })
                .catch(error => {
                    console.error('Logout error:', error);
                    alert('Logout failed. Try again.');
                });
        });
    
        // Init dashboard
        document.addEventListener('DOMContentLoaded', function () {
            loadDashboardData();
            setInterval(loadDashboardData, 30000); // Refresh every 30 seconds
        });







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


      const pic = localStorage.getItem("userProfileImage");
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

            fetch("http://localhost:8080/api/update-username", {
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

            fetch("http://localhost:8080/api/update-phone", {
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


      const pic = localStorage.getItem("userProfileImage");
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
          localStorage.setItem("userProfileImage", base64Image);
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


    









// Show the notification when a new message is received
function showCustomNotification() {
  const notificationArea = document.getElementById("notificationArea");
  notificationArea.style.display = "block";  // Show the notification

  // Event listener to hide the notification when clicked
  notificationArea.addEventListener('click', () => {
    notificationArea.style.display = "none";  // Hide on click
  });

  // Hide the notification after 3 seconds if not clicked
  setTimeout(() => {
    if (notificationArea.style.display !== "none") {
      notificationArea.style.display = "none";
    }
  }, 3000);
}








let currentUserId = 'USER_0001'; // Replace with actual logged-in ID
let isAdmin = false; // true if admin, false if user
let receiverId = 'ADMIN_0001'; // Replace with actual receiver (admin ID)

// Fetch chat messages from backend
function loadChatMessages() {
  fetch(`http://localhost:8080/chat/conversation?userId=${currentUserId}&adminId=ADMIN_0001`) // Pass userId and adminId
    .then(res => res.json())
    .then(data => {
      const chatBox = document.getElementById('chatBox');
      chatBox.innerHTML = ''; // Clear previous messages

      data.forEach(msg => {
        const div = document.createElement('div');
        div.classList.add('chat-message');

        // Add different class based on sender
        if (msg.senderId === currentUserId) {
          div.classList.add('user-message');
        } else {
          div.classList.add('admin-message');
        }

        // Format timestamp to time only
        const timeOnly = new Date(msg.timestamp).toLocaleTimeString('en-IN', {
          hour: '2-digit',
          minute: '2-digit',
          hour12: true,
          timeZone: 'Asia/Kolkata'
        });

        // Combine message and time
        div.innerHTML = `
          <span>${msg.message}</span>
          <br>
          <small class="timestamp">${timeOnly}</small>
        `;

        chatBox.appendChild(div);
      });

      chatBox.scrollTop = chatBox.scrollHeight; // Scroll to the bottom of the chat
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
    senderId: currentUserId,
    receiverId: receiverId,
    message: message,
    timestamp: new Date().toISOString() // Add timestamp
  };

  fetch('http://localhost:8080/chat/send', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  })
    .then(res => {
      if (res.ok) {
        input.value = ''; // Clear the input field
        loadChatMessages(); // Reload chat after sending
      }
    })
    .catch(err => {
      console.error('Error sending message:', err);
    });
}

// Auto-refresh chat every 5 seconds
setInterval(loadChatMessages, 10000);
window.onload = loadChatMessages; // Load chat on page load






    
    