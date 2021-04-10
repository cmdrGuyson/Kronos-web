<div
        class="modal fade"
        id="addModal"
        tabindex="-1"
        role="dialog"
        aria-labelledby="manageGroupModalLabel"
        aria-hidden="true"
>
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    Add Lecture
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
                        <form method="POST" action="/add-lecture">
                            <div class="form-row">
                                <div class="col">
                                    <label for="inputDate">Date</label>
                                    <input type="date" class="form-control select-filter" name="date" id="inputDate" required>
                                </div>
                                <div class="col">
                                    <label for="inputTime">Start Time</label>
                                    <input type="time" class="form-control select-filter" name="startTime" id="inputTime" required>
                                </div>
                                <div class="col">
                                    <label for="inputDuration">Duration</label>
                                    <input type="number" class="form-control select-filter" id="inputDuration" name="duration" required>
                                </div>
                            </div>
                            <div class="form-row form-row-modal">
                                <label for="inputModule">Module</label>
                                <select class="custom-select form-control select-filter" id="inputModule" name="moduleID" required>
                                    <c:forEach var="module" items="${modules}">
                                        <option value="${module.getModuleID()}" >${module.getName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-row form-row-modal">
                                <label for="inputRoom">Room</label>
                                <select class="custom-select form-control select-filter" id="inputRoom" name="roomID" required>
                                    <c:forEach var="room" items="${rooms}">
                                        <option value="${room.getRoomID()}" >${room.getType()}-${room.getRoomID()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <hr />
                            <button class="btn btn-primary" type="submit" value="submit">
                                Add Lecture
                            </button>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<script>
    let today = new Date();
    let dd = today.getDate();
    let mm = today.getMonth() + 1;
    const yyyy = today.getFullYear();
    if(dd<10){
        dd='0'+dd
    }
    if(mm<10){
        mm='0'+mm
    }

    today = yyyy+'-'+mm+'-'+dd;
    document.getElementById("inputDate").setAttribute("min", today);
</script>