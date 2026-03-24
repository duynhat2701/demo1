import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

// ================= EXCEPTION =================
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}

class InvalidStudentTypeException extends Exception {
    public InvalidStudentTypeException(String message) {
        super(message);
    }
}

// ================= LỚP CHA =================
abstract class HocVien {
    private String MaHV;
    private String HoTen;
    private double DiemGoc;

    public HocVien(String maHV, String hoTen, double diemGoc) throws InvalidScoreException {
        this.MaHV = maHV;
        this.HoTen = hoTen;
        setDiemGoc(diemGoc);
    }

    public String getMaHV() {
        return MaHV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public double getDiemGoc() {
        return DiemGoc;
    }

    public void setDiemGoc(double diemGoc) throws InvalidScoreException {
        if (diemGoc < 0 || diemGoc > 10) {
            throw new InvalidScoreException("Điểm phải từ 0 đến 10");
        }
        this.DiemGoc = diemGoc;
    }

    // ===== POLYMORPHISM =====
    public double tinhDiemXet() {
        return DiemGoc;
    }

    public String xepLoai() {
        double d = tinhDiemXet();
        if (d >= 8) return "Giỏi";
        else if (d >= 6) return "Khá";
        else if (d >= 4) return "Trung bình";
        else return "Yếu";
    }

    public boolean dat() {
        return tinhDiemXet() >= 5;
    }

    @Override
    public String toString() {
        return MaHV + " - " + HoTen +
                " | Điểm: " + tinhDiemXet() +
                " | Xếp loại: " + xepLoai();
    }
}

// ================= HỌC VIÊN THƯỜNG =================
class HocVienThuong extends HocVien {
    public HocVienThuong(String maHV, String hoTen, double diemGoc)
            throws InvalidScoreException {
        super(maHV, hoTen, diemGoc);
    }
}

// ================= HỌC VIÊN ƯU TIÊN =================
class HocVienUuTien extends HocVien {
    private double diemCong;

    public HocVienUuTien(String maHV, String hoTen, double diemGoc, double diemCong)
            throws InvalidScoreException {
        super(maHV, hoTen, diemGoc);
        this.diemCong = diemCong;
    }

    @Override
    public double tinhDiemXet() {
        return Math.min(10, getDiemGoc() + diemCong);
    }
}

// ================= SERVICE =================
class HocVienService {

    // ===== STREAM: TÊN =====
    public static ArrayList<String> danhSachTen(ArrayList<HocVien> list) {
        return list.stream()
                .map(HocVien::getHoTen)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // ===== STREAM: ĐIỂM =====
    public static ArrayList<Double> danhSachDiem(ArrayList<HocVien> list) {
        return list.stream()
                .map(HocVien::tinhDiemXet)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // ===== STREAM: HỌC VIÊN ĐẠT =====
    public static ArrayList<HocVien> danhSachDat(ArrayList<HocVien> list) {
        return list.stream()
                .filter(HocVien::dat)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // ===== STREAM: HỌC VIÊN GIỎI =====
    public static ArrayList<String> hocVienGioi(ArrayList<HocVien> list) {
        return list.stream()
                .filter(hv -> hv.tinhDiemXet() >= 8)
                .map(hv -> hv.getMaHV() + " - " + hv.getHoTen())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // ===== TRUYỀN THỐNG =====
    public static double tinhTB(ArrayList<HocVien> list) {
        double sum = 0;
        for (HocVien hv : list) {
            sum += hv.tinhDiemXet();
        }
        return list.isEmpty() ? 0 : sum / list.size();
    }

    public static int demDat(ArrayList<HocVien> list) {
        int count = 0;
        for (HocVien hv : list) {
            if (hv.dat()) count++;
        }
        return count;
    }

    public static HocVien max(ArrayList<HocVien> list) {
        HocVien m = list.get(0);
        for (HocVien hv : list) {
            if (hv.tinhDiemXet() > m.tinhDiemXet()) m = hv;
        }
        return m;
    }

    public static HocVien min(ArrayList<HocVien> list) {
        HocVien m = list.get(0);
        for (HocVien hv : list) {
            if (hv.tinhDiemXet() < m.tinhDiemXet()) m = hv;
        }
        return m;
    }
}

// ================= MAIN =================
public class Main {
    static Scanner sc = new Scanner(System.in);

    // ===== NHẬP ĐIỂM =====
    public static double nhapDiem() {
        while (true) {
            try {
                System.out.print("Nhập điểm (0-10): ");
                String input = sc.nextLine();
                double diem = Double.parseDouble(input);

                if (diem < 0 || diem > 10) {
                    throw new InvalidScoreException("Điểm phải từ 0 đến 10");
                }
                return diem;

            } catch (NumberFormatException e) {
                System.out.println("Sai định dạng số!");
            } catch (InvalidScoreException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // ===== NHẬP LOẠI =====
    public static int nhapLoai() {
        while (true) {
            try {
                System.out.print("Loại (1-Thường | 2-Ưu tiên): ");
                String input = sc.nextLine();
                int loai = Integer.parseInt(input);

                if (loai != 1 && loai != 2) {
                    throw new InvalidStudentTypeException("Loại học viên không hợp lệ, nhập lại");
                }
                return loai;

            } catch (NumberFormatException e) {
                System.out.println("Phải nhập số 1 hoặc 2!");
            } catch (InvalidStudentTypeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<HocVien> list = new ArrayList<>();

        try {
            System.out.print("Nhập số học viên: ");
            int n = Integer.parseInt(sc.nextLine());

            for (int i = 0; i < n; i++) {
                System.out.println("\n--- HV " + (i + 1) + " ---");

                System.out.print("Mã HV: ");
                String ma = sc.nextLine();

                System.out.print("Họ tên: ");
                String ten = sc.nextLine();

                double diem = nhapDiem();
                int loai = nhapLoai();

                if (loai == 2) {
                    System.out.print("Điểm cộng: ");
                    double cong = Double.parseDouble(sc.nextLine());
                    list.add(new HocVienUuTien(ma, ten, diem, cong));
                } else {
                    list.add(new HocVienThuong(ma, ten, diem));
                }
            }

        } catch (Exception e) {
            System.out.println("Lỗi hệ thống: " + e.getMessage());
        } finally {
            System.out.println("\n=== KẾT THÚC NHẬP LIỆU ===");
        }

        // ================= OUTPUT =================
        System.out.println("\n=== DANH SÁCH ===");
        for (HocVien hv : list) {
            System.out.println(hv);
        }

        System.out.println("\n=== BÁO CÁO ===");
        System.out.println("TB: " + HocVienService.tinhTB(list));
        System.out.println("Đạt: " + HocVienService.demDat(list));
        System.out.println("Không đạt: " + (list.size() - HocVienService.demDat(list)));
        System.out.println("Cao nhất: " + HocVienService.max(list));
        System.out.println("Thấp nhất: " + HocVienService.min(list));

        // ================= STREAM API =================
        System.out.println("\n=== STREAM API ===");

        System.out.println("Danh sách tên:");
        System.out.println(HocVienService.danhSachTen(list));

        System.out.println("Danh sách điểm:");
        System.out.println(HocVienService.danhSachDiem(list));

        System.out.println("Học viên đạt:");
        System.out.println(HocVienService.danhSachDat(list));

        System.out.println("Học viên giỏi:");
        System.out.println(HocVienService.hocVienGioi(list));
    }
}