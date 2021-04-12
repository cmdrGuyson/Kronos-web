<div
        class="modal fade"
        id="settingsModal"
        tabindex="-1"
        role="dialog"
        aria-labelledby="manageGroupModalLabel"
        aria-hidden="true"
>
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    Change Password
                </h5>
                <button
                        type="button"
                        class="close"
                        data-dismiss="modal"
                        aria-label="Close"
                >
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>



            <div class="modal-body">

                <%@ include file="success_modal.jsp" %>
                <%@ include file="error_modal.jsp" %>

                <div class="card-body">
                    <div class="form-body">
                        <form method="POST" action="/change-password" oninput='confirmPassword.setCustomValidity(confirmPassword.value != newPassword.value ? "Passwords do not match." : "")';>
                            <div class="form-row">
                                <div class="col">
                                    <label for="inputOldPassword">Old Password</label>
                                    <input type="password" class="form-control select-filter" name="oldPassword" id="inputOldPassword" required>
                                </div>
                            </div>
                            <div class="form-row form-row-model-text">
                                <div class="col">
                                    <label for="inputNewPassword">New Password</label>
                                    <input type="password" class="form-control select-filter" name="newPassword" id="inputNewPassword" required>
                                </div>
                            </div>
                            <div class="form-row form-row-model-text">
                                <div class="col">
                                    <label for="inputConfirmPassword">Confirm Password</label>
                                    <input type="password" class="form-control select-filter" name="confirmPassword" id="inputConfirmPassword" required>
                                </div>
                            </div>
                            <hr />
                            <button class="btn btn-primary" type="submit" value="submit">
                                Change Password
                            </button>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>