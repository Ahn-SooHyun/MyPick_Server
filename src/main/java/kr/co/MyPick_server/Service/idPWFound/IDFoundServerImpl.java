package kr.co.MyPick_server.Service.idPWFound;

import kr.co.MyPick_server.DTO.idPWFound.IDFoundReq;

public interface IDFoundServerImpl {

    /**
     * Searches for a user's ID based on the provided request data.
     *
     * @param idFoundReq An object containing the user's information, such as name and email,
     *                   which are required to find the user ID.
     * @return The user's ID as a String if found.
     *         Returns null if no matching user is found.
     */
    String idFound(IDFoundReq idFoundReq);
}
