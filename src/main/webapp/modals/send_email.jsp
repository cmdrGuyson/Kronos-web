<div
        class="modal fade"
        id="contactModal"
        tabindex="-1"
        role="dialog"
        aria-labelledby="manageGroupModalLabel"
        aria-hidden="true"
>
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    Contact Administrator
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
                <div class="card-body">
                    <div class="form-body">
                        <form method="POST" action="/contact">
                            <div class="form-row" style="padding: 5px;">
                                <label for="inputEmail">Your email</label>
                                <input type="email" class="form-control select-filter" id="inputEmail" name="email" required>
                            </div>
                            <div class="form-row form-row-model-text">
                                <div class="col">
                                    <label for="inputBody">Body</label>
                                    <textarea class="form-control text-area-round" id="inputBody" name="body" rows="4" required></textarea>
                                </div>
                            </div>
                            <hr />
                            <button class="btn btn-primary" type="submit" value="submit">
                                Send Email
                            </button>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>