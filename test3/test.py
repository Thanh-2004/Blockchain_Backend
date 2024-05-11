
import socket

# Khởi tạo socket
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Xác định địa chỉ và cổng của server
server_address = ('localhost', 12345)

# Ràng buộc socket với địa chỉ và cổng đã xác định
server_socket.bind(server_address)

# Lắng nghe kết nối đến socket
server_socket.listen(5)

print("Đang chờ kết nối từ client...")

while True:
    # Chấp nhận kết nối từ client
    client_socket, client_address = server_socket.accept()
    print("Đã kết nối với", client_address)

    while True:
        # Nhận dữ liệu từ client
        print('Đang chờ nhận dữ liệu từ client...')
        data = client_socket.recv(1024)
        if not data:
            print("Client đã đóng kết nối.")
            break
        
        print("Đang xử lý...")

        # In dữ liệu nhận được từ client
        query = data.decode()
        print("Dữ liệu nhận được từ client:", query)

        # Gửi phản hồi cho client
        print("Tiến hành tìm kiếm...")
        # result = search_engine.search(query)
        result = [2, 1, 3, 4, 5, 6, 7, 8, 9, 10]
        response = " ".join(str(item) for item in result) + "\n"

        print("Gửi phản hồi cho client:", response)
        client_socket.sendall(response.encode())
        print("Đã gửi phản hồi.")

    # Đóng kết nối với client
    # client_socket.close()

# Đóng socket của server
server_socket.close()

