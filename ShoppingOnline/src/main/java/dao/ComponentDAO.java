package dao;

import connect.DBConnection;
import model.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComponentDAO {
    
    // Logger để ghi lại lỗi nếu có
    private static final Logger LOGGER = Logger.getLogger(ComponentDAO.class.getName());

    // Phương thức phụ trợ
    private Long getGeneratedKey(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        }
        return null;
    }

    // ✅ PHƯƠNG THỨC SỬA ĐỔI: Kiểm tra tồn tại, nhận Connection
    private Long checkExistingComponent(Connection conn, String tableName, String code) throws SQLException {
        String checkSql = "SELECT Id FROM " + tableName + " WHERE Code = ?";
        try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("Id");
                }
            }
        }
        return null;
    }

    
    // ✅ PHƯƠNG THỨC SỬA ĐỔI: CPU, nhận Connection
    public Long saveAndGetCpuId(Cpu cpu, Connection conn) throws SQLException {
        try {
            Long existingId = checkExistingComponent(conn, "Cpu", cpu.getCode());
            if (existingId != null) return existingId;

            String insertSql = "INSERT INTO Cpu (Code, Name, Cores, Threads, BaseClockGHz, TurboClockGHz) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                
                ps.setString(1, cpu.getCode());
                ps.setString(2, cpu.getName());
                
                if(cpu.getCores() != null) ps.setInt(3, cpu.getCores()); else ps.setNull(3, Types.INTEGER);
                if(cpu.getThreads() != null) ps.setInt(4, cpu.getThreads()); else ps.setNull(4, Types.INTEGER);
                if(cpu.getBaseClockGHz() != null) ps.setFloat(5, cpu.getBaseClockGHz()); else ps.setNull(5, Types.FLOAT);
                if(cpu.getTurboClockGHz() != null) ps.setFloat(6, cpu.getTurboClockGHz()); else ps.setNull(6, Types.FLOAT);
                ps.executeUpdate();
                return getGeneratedKey(ps);
            }
        } catch (SQLException e) {
            // Log lỗi để dễ dàng debug lỗi UNIQUE/NOT NULL
            LOGGER.log(Level.SEVERE, "Lỗi khi chèn/lấy ID CPU: " + e.getMessage());
            throw e; // Ném ra ngoài để Servlet xử lý rollback
        }
    }

    
    // ✅ PHƯƠNG THỨC SỬA ĐỔI: GPU, nhận Connection
    public Long saveAndGetGpuId(Gpu gpu, Connection conn) throws SQLException {
        try {
            Long existingId = checkExistingComponent(conn, "Gpu", gpu.getCode());
            if (existingId != null) return existingId;

            String insertSql = "INSERT INTO Gpu (Code, Name, VRAM_GB) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, gpu.getCode());
                ps.setString(2, gpu.getName());
                
                if(gpu.getVramGB() != null) ps.setInt(3, gpu.getVramGB()); else ps.setNull(3, Types.INTEGER);
                
                ps.executeUpdate();
                return getGeneratedKey(ps);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi chèn/lấy ID GPU: " + e.getMessage());
            throw e;
        }
    }

    
    // ✅ PHƯƠNG THỨC SỬA ĐỔI: RAM, nhận Connection
    public Long saveAndGetRamId(Ram ram, Connection conn) throws SQLException {
        try {
            Long existingId = checkExistingComponent(conn, "Ram", ram.getCode());
            if (existingId != null) return existingId;

            String insertSql = "INSERT INTO Ram (Code, SizeGB, Type) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, ram.getCode());
                
                if(ram.getSizeGB() != null) ps.setInt(2, ram.getSizeGB()); else ps.setNull(2, Types.INTEGER);
                ps.setString(3, ram.getType());
                
                ps.executeUpdate();
                return getGeneratedKey(ps);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi chèn/lấy ID RAM: " + e.getMessage());
            throw e;
        }
    }

    
    // ✅ PHƯƠNG THỨC SỬA ĐỔI: Storage, nhận Connection
    public Long saveAndGetStorageId(Storage storage, Connection conn) throws SQLException {
        try {
            Long existingId = checkExistingComponent(conn, "Storage", storage.getCode());
            if (existingId != null) return existingId;

            String insertSql = "INSERT INTO Storage (Code, CapacityGB, Type) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, storage.getCode());
                
                if(storage.getCapacityGB() != null) ps.setInt(2, storage.getCapacityGB()); else ps.setNull(2, Types.INTEGER);
                ps.setString(3, storage.getType());
                
                ps.executeUpdate();
                return getGeneratedKey(ps);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi chèn/lấy ID Storage: " + e.getMessage());
            throw e;
        }
    }

    
    // ✅ PHƯƠNG THỨC SỬA ĐỔI: Screen, nhận Connection
    public Long saveAndGetScreenId(Screen screen, Connection conn) throws SQLException {
        try {
            Long existingId = checkExistingComponent(conn, "Screen", screen.getCode());
            if (existingId != null) return existingId;
            
            String insertSql = "INSERT INTO Screen (Code, SizeInch, Resolution, PanelType, RefreshRate) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, screen.getCode());
                
                if(screen.getSizeInch() != null) ps.setFloat(2, screen.getSizeInch()); else ps.setNull(2, Types.FLOAT);
                ps.setString(3, screen.getResolution());
                ps.setString(4, screen.getPanelType());
                if(screen.getRefreshRate() != null) ps.setInt(5, screen.getRefreshRate()); else ps.setNull(5, Types.INTEGER);
                
                ps.executeUpdate();
                return getGeneratedKey(ps);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi chèn/lấy ID Screen: " + e.getMessage());
            throw e;
        }
    }
    // Lấy thông tin CPU theo ID
    public Cpu getCpuById(Long id) {
        if (id == null) return null;
        String sql = "SELECT * FROM Cpu WHERE Id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cpu c = new Cpu();
                    c.setId(rs.getLong("Id"));
                    c.setCode(rs.getString("Code"));
                    c.setName(rs.getString("Name"));
                    c.setCores(rs.getObject("Cores") != null ? rs.getInt("Cores") : null);
                    c.setThreads(rs.getObject("Threads") != null ? rs.getInt("Threads") : null);
                    c.setBaseClockGHz(rs.getObject("BaseClockGHz") != null ? rs.getFloat("BaseClockGHz") : null);
                    c.setTurboClockGHz(rs.getObject("TurboClockGHz") != null ? rs.getFloat("TurboClockGHz") : null);
                    return c;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // Tương tự cho các linh kiện khác (GPU, RAM, Storage, Screen)
    // Ví dụ GPU:
    public Gpu getGpuById(Long id) {
        if (id == null) return null;
        String sql = "SELECT * FROM Gpu WHERE Id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Gpu g = new Gpu();
                    g.setId(rs.getLong("Id"));
                    g.setCode(rs.getString("Code"));
                    g.setName(rs.getString("Name"));
                    g.setVramGB(rs.getObject("VRAM_GB") != null ? rs.getInt("VRAM_GB") : null);
                    return g;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
   // ---------------------------------------------------------
    // 3. Lấy thông tin RAM theo ID
    // ---------------------------------------------------------
    public Ram getRamById(Long id) {
        if (id == null) return null;
        String sql = "SELECT * FROM Ram WHERE Id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ram r = new Ram();
                    r.setId(rs.getLong("Id"));
                    r.setCode(rs.getString("Code"));
                    // Kiểm tra null cho trường số
                    r.setSizeGB(rs.getObject("SizeGB") != null ? rs.getInt("SizeGB") : null);
                    r.setType(rs.getString("Type"));
                    return r;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---------------------------------------------------------
    // 4. Lấy thông tin Storage (Ổ cứng) theo ID
    // ---------------------------------------------------------
    public Storage getStorageById(Long id) {
        if (id == null) return null;
        String sql = "SELECT * FROM Storage WHERE Id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Storage s = new Storage();
                    s.setId(rs.getLong("Id"));
                    s.setCode(rs.getString("Code"));
                    // Kiểm tra null cho trường số
                    s.setCapacityGB(rs.getObject("CapacityGB") != null ? rs.getInt("CapacityGB") : null);
                    s.setType(rs.getString("Type"));
                    return s;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---------------------------------------------------------
    // 5. Lấy thông tin Screen (Màn hình) theo ID
    // ---------------------------------------------------------
    public Screen getScreenById(Long id) {
        if (id == null) return null;
        String sql = "SELECT * FROM Screen WHERE Id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Screen sc = new Screen();
                    sc.setId(rs.getLong("Id"));
                    sc.setCode(rs.getString("Code"));
                    // Kiểm tra null cho Float và Int
                    sc.setSizeInch(rs.getObject("SizeInch") != null ? rs.getFloat("SizeInch") : null);
                    sc.setResolution(rs.getString("Resolution"));
                    sc.setPanelType(rs.getString("PanelType"));
                    sc.setRefreshRate(rs.getObject("RefreshRate") != null ? rs.getInt("RefreshRate") : null);
                    return sc;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}